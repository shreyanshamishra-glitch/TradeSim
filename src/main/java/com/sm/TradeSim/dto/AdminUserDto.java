package com.sm.TradeSim.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Dummy admin user profile (no login required yet)")
public class AdminUserDto {

    private final String username;
    private final String role;
    private final String displayName;
}
