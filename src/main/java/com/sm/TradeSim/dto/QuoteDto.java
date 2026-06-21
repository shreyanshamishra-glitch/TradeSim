package com.sm.TradeSim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Normalized global quote for a security symbol")
public class QuoteDto {

    @Schema(example = "IBM")
    private final String symbol;

    @Schema(example = "251.3800")
    private final String open;

    @Schema(example = "252.4700")
    private final String high;

    @Schema(example = "243.6800")
    private final String low;

    @Schema(example = "249.1000")
    private final String price;

    @Schema(example = "16619749")
    private final String volume;

    @Schema(example = "2026-06-18")
    private final String latestTradingDay;

    @Schema(example = "262.3500")
    private final String previousClose;

    @Schema(example = "-13.2500")
    private final String change;

    @Schema(example = "-5.0505%")
    private final String changePercent;
}
