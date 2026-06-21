package com.sm.TradeSim.service;

import com.sm.TradeSim.config.AppProperties;
import com.sm.TradeSim.dto.CreateOrderRequest;
import com.sm.TradeSim.dto.OrderDto;
import com.sm.TradeSim.dto.PageResponse;
import com.sm.TradeSim.entity.Order;
import com.sm.TradeSim.entity.OrderSide;
import com.sm.TradeSim.entity.OrderStatus;
import com.sm.TradeSim.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MarketDataService marketDataService;
    private final AppProperties appProperties;

    @Transactional(readOnly = true)
    public PageResponse<OrderDto> listOrders(int page, int size) {
        int safeSize = clampPageSize(size);
        Pageable pageable = PageRequest.of(page, safeSize);
        Page<Order> orderPage = orderRepository.findAllByOrderByTimestampDesc(pageable);

        return PageResponse.<OrderDto>builder()
                .content(orderPage.getContent().stream().map(this::toDto).toList())
                .page(orderPage.getNumber())
                .size(orderPage.getSize())
                .totalElements(orderPage.getTotalElements())
                .totalPages(orderPage.getTotalPages())
                .maxPageSize(appProperties.getPagination().getMaxPageSize())
                .build();
    }

    @Transactional
    public OrderDto createOrder(CreateOrderRequest request) {
        Order order = new Order();
        order.setId(generateOrderId());
        order.setSecurityName(request.getSecurityName().trim().toUpperCase());
        order.setSide(request.getSide());
        order.setQuantity(request.getQuantity());
        order.setPrice(request.getPrice());
        order.setStatus(resolveStatus(request));
        order.setTimestamp(Instant.now());

        return toDto(orderRepository.save(order));
    }

    @Transactional
    public List<OrderDto> generateDummyOrders(int count, boolean useLiveQuotes) {
        int safeCount = Math.min(
                Math.max(count, 1),
                appProperties.getPagination().getMaxDummyOrders());

        List<String> symbols = appProperties.getDefaultSymbols();
        ThreadLocalRandom random = ThreadLocalRandom.current();

        return random.ints(safeCount, 0, symbols.size())
                .mapToObj(index -> {
                    String symbol = symbols.get(index);
                    OrderSide side = random.nextBoolean() ? OrderSide.BUY : OrderSide.SELL;
                    int quantity = random.nextInt(1, 100);

                    double price = resolvePrice(symbol, useLiveQuotes, random);

                    CreateOrderRequest request = new CreateOrderRequest();
                    request.setSecurityName(symbol);
                    request.setSide(side);
                    request.setQuantity(quantity);
                    request.setPrice(price);
                    return createOrder(request);
                })
                .toList();
    }

    private double resolvePrice(String symbol, boolean useLiveQuotes, ThreadLocalRandom random) {
        if (useLiveQuotes) {
            try {
                return marketDataService.getLatestPrice(symbol);
            } catch (RuntimeException ignored) {
                // Fall back to synthetic price when Alpha Vantage rate-limits or fails.
            }
        }
        return Math.round(random.nextDouble(50, 500) * 100.0) / 100.0;
    }

    private OrderStatus resolveStatus(CreateOrderRequest request) {
        if (request.getQuantity() <= 0 || request.getPrice() <= 0) {
            return OrderStatus.REJECTED;
        }
        return OrderStatus.PENDING;
    }

    private String generateOrderId() {
        long next = orderRepository.count() + 1001;
        return "ORD-" + next;
    }

    private int clampPageSize(int size) {
        int max = appProperties.getPagination().getMaxPageSize();
        if (size <= 0) {
            return appProperties.getPagination().getDefaultPageSize();
        }
        return Math.min(size, max);
    }

    private OrderDto toDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .securityName(order.getSecurityName())
                .side(order.getSide())
                .quantity(order.getQuantity())
                .price(order.getPrice())
                .status(order.getStatus())
                .timestamp(order.getTimestamp())
                .build();
    }
}
