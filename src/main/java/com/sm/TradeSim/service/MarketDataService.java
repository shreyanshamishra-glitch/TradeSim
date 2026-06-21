package com.sm.TradeSim.service;

import com.sm.TradeSim.config.AppProperties;
import com.sm.TradeSim.dto.AlphaVantageQuote;
import com.sm.TradeSim.dto.GlobalQuoteResponse;
import com.sm.TradeSim.dto.QuoteDto;
import com.sm.TradeSim.exception.QuoteNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class MarketDataService {

    private final RestTemplate restTemplate;
    private final AppProperties appProperties;

    public QuoteDto getGlobalQuote(String symbol) {
        String normalizedSymbol = symbol.trim().toUpperCase();

        String url = UriComponentsBuilder
                .fromUriString(appProperties.getAlphaVantage().getBaseUrl())
                .queryParam("function", "GLOBAL_QUOTE")
                .queryParam("symbol", normalizedSymbol)
                .queryParam("apikey", appProperties.getAlphaVantage().getApiKey())
                .toUriString();

        GlobalQuoteResponse response = restTemplate.getForObject(url, GlobalQuoteResponse.class);

        if (response == null || response.getGlobalQuote() == null) {
            throw new QuoteNotFoundException(normalizedSymbol);
        }

        AlphaVantageQuote quote = response.getGlobalQuote();
        if (quote.getPrice() == null || quote.getPrice().isBlank()) {
            throw new QuoteNotFoundException(normalizedSymbol);
        }

        return mapToDto(quote);
    }

    public Double getLatestPrice(String symbol) {
        QuoteDto quote = getGlobalQuote(symbol);
        return Double.parseDouble(quote.getPrice());
    }

    private QuoteDto mapToDto(AlphaVantageQuote quote) {
        return QuoteDto.builder()
                .symbol(quote.getSymbol())
                .open(quote.getOpen())
                .high(quote.getHigh())
                .low(quote.getLow())
                .price(quote.getPrice())
                .volume(quote.getVolume())
                .latestTradingDay(quote.getLatestTradingDay())
                .previousClose(quote.getPreviousClose())
                .change(quote.getChange())
                .changePercent(quote.getChangePercent())
                .build();
    }
}
