package com.sm.TradeSim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Pagination limits exposed to the UI")
public class PaginationConfig {

    private final int maxPageSize;
    private final int defaultPageSize;
}
