package com.sm.TradeSim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@Schema(description = "API metadata for dynamic UI discovery")
public class ApiMetadataResponse {

    private final List<EndpointDescriptor> endpoints;
    private final PaginationConfig pagination;
    private final Map<String, AttributeConfig> attributes;
    private final List<String> defaultSymbols;
}
