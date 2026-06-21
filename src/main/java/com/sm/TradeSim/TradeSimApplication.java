package com.sm.TradeSim;

import com.sm.TradeSim.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class TradeSimApplication {

    public static void main(String[] args) {
        SpringApplication.run(TradeSimApplication.class, args);
    }
}
