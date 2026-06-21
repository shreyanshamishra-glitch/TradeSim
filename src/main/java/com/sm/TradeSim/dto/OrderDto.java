package com.sm.TradeSim.dto;

import com.sm.TradeSim.entity.OrderSide;
import com.sm.TradeSim.entity.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
@Schema(description = "Trade order returned from the OMS API")
public class OrderDto {

    @Schema(example = "ORD-1001")
    private final String id;

    @Schema(example = "IBM")
    private final String securityName;

    @Schema(example = "BUY")
    private final OrderSide side;

    @Schema(example = "10")
    private final int quantity;

    @Schema(example = "249.10")
    private final double price;

    @Schema(example = "PENDING")
    private final OrderStatus status;

    @Schema(example = "2026-06-21T10:30:00Z")
    private final Instant timestamp;
}
