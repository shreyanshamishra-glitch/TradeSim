package com.sm.TradeSim.dto;

import com.sm.TradeSim.entity.OrderSide;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Payload for creating a new trade order")
public class CreateOrderRequest {

    @NotBlank
    @Schema(example = "IBM")
    private String securityName;

    @NotNull
    @Schema(example = "BUY")
    private OrderSide side;

    @Min(1)
    @Schema(example = "10")
    private int quantity;

    @DecimalMin("0.01")
    @Schema(example = "249.10")
    private double price;
}
