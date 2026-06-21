package com.sm.TradeSim.exception;

public class QuoteNotFoundException extends RuntimeException {

    public QuoteNotFoundException(String symbol) {
        super("Quote not found for symbol: " + symbol);
    }
}
