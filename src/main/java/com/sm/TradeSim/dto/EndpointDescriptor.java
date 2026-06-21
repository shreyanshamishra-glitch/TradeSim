package com.sm.TradeSim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "REST endpoint descriptor for UI binding")
public class EndpointDescriptor {

    private final String id;
    private final String name;
    private final String method;
    private final String path;
    private final String description;
    private final List<String> queryParams;
}
