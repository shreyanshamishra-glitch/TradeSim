package com.sm.TradeSim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Field constraints for forms and tables")
public class AttributeConfig {

    private final String label;
    private final String type;
    private final boolean required;
    private final Number min;
    private final Number max;
    private final String[] allowedValues;
}
