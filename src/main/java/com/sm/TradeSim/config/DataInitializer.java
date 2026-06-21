package com.sm.TradeSim.config;

import com.sm.TradeSim.entity.Order;
import com.sm.TradeSim.entity.OrderSide;
import com.sm.TradeSim.entity.OrderStatus;
import com.sm.TradeSim.entity.User;
import com.sm.TradeSim.repository.OrderRepository;
import com.sm.TradeSim.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Bean
    CommandLineRunner seedData() {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin123");
                admin.setRole("ADMIN");
                admin.setDisplayName("Demo Admin");
                userRepository.save(admin);
            }

            if (orderRepository.count() == 0) {
                seedOrder("ORD-1001", "IBM", OrderSide.BUY, 10, 249.10, OrderStatus.PENDING);
                seedOrder("ORD-1002", "MSFT", OrderSide.SELL, 5, 420.50, OrderStatus.COMPLETED);
                seedOrder("ORD-1003", "AAPL", OrderSide.BUY, 20, 195.25, OrderStatus.REJECTED);
            }
        };
    }

    private void seedOrder(
            String id,
            String security,
            OrderSide side,
            int quantity,
            double price,
            OrderStatus status) {
        Order order = new Order();
        order.setId(id);
        order.setSecurityName(security);
        order.setSide(side);
        order.setQuantity(quantity);
        order.setPrice(price);
        order.setStatus(status);
        order.setTimestamp(Instant.now());
        orderRepository.save(order);
    }
}
