package com.sm.TradeSim.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Pagination pagination = new Pagination();
    private AlphaVantage alphaVantage = new AlphaVantage();
    private List<String> defaultSymbols = List.of("IBM", "MSFT", "AAPL", "GOOGL", "TSLA");

    @Getter
    @Setter
    public static class Pagination {
        private int maxPageSize = 50;
        private int defaultPageSize = 10;
        private int maxDummyOrders = 25;
    }

    @Getter
    @Setter
    public static class AlphaVantage {
        private String apiKey = "demo";
        private String baseUrl = "https://www.alphavantage.co/query";
    }
}
