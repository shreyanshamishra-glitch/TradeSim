package com.sm.TradeSim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Request to generate dummy orders for testing")
public class GenerateDummyOrdersRequest {

    @Min(1)
    @Max(100)
    @Schema(example = "5", description = "Number of dummy orders to create (capped by server config)")
    private int count = 5;

    @Schema(example = "true", description = "When true, prices are derived from live quotes when available")
    private boolean useLiveQuotes = true;
}
