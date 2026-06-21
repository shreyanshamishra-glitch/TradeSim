package com.sm.TradeSim.controller;

import com.sm.TradeSim.dto.AdminUserDto;
import com.sm.TradeSim.dto.ApiMetadataResponse;
import com.sm.TradeSim.dto.CreateOrderRequest;
import com.sm.TradeSim.dto.GenerateDummyOrdersRequest;
import com.sm.TradeSim.dto.OrderDto;
import com.sm.TradeSim.dto.PageResponse;
import com.sm.TradeSim.dto.QuoteDto;
import com.sm.TradeSim.service.AdminService;
import com.sm.TradeSim.service.ApiMetadataService;
import com.sm.TradeSim.service.MarketDataService;
import com.sm.TradeSim.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/analysis")
@RequiredArgsConstructor
@Tag(name = "Security Analysis", description = "Market data and order management APIs")
public class SecurityAnalysis {

    private final MarketDataService marketDataService;
    private final OrderService orderService;
    private final ApiMetadataService apiMetadataService;
    private final AdminService adminService;

    @GetMapping("/security")
    @Operation(summary = "Health check for analysis module")
    public String getSecurity() {
        return "ok";
    }

    @GetMapping("/security/quote")
    @Operation(summary = "Get global quote for a symbol via Alpha Vantage")
    public QuoteDto getQuote(
            @Parameter(description = "Ticker symbol", example = "IBM")
            @RequestParam String symbol) {
        return marketDataService.getGlobalQuote(symbol);
    }

    @GetMapping("/metadata")
    @Operation(summary = "API metadata for dynamic UI binding")
    public ApiMetadataResponse getMetadata() {
        return apiMetadataService.getMetadata();
    }

    @GetMapping("/admin")
    @Operation(summary = "Dummy admin user profile")
    public AdminUserDto getAdminUser() {
        return adminService.getDummyAdmin();
    }

    @GetMapping("/orders")
    @Operation(summary = "List orders with pagination")
    public PageResponse<OrderDto> listOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return orderService.listOrders(page, size);
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new order")
    public OrderDto createOrder(@Valid @RequestBody CreateOrderRequest request) {
        return orderService.createOrder(request);
    }

    @PostMapping("/orders/generate")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Generate dummy orders for testing")
    public List<OrderDto> generateDummyOrders(@Valid @RequestBody GenerateDummyOrdersRequest request) {
        return orderService.generateDummyOrders(request.getCount(), request.isUseLiveQuotes());
    }
}
