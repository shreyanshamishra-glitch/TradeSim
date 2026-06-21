package com.sm.TradeSim.service;

import com.sm.TradeSim.dto.AdminUserDto;
import com.sm.TradeSim.entity.User;
import com.sm.TradeSim.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private static final String DEFAULT_ADMIN_USERNAME = "admin";

    private final UserRepository userRepository;

    public AdminUserDto getDummyAdmin() {
        User admin = userRepository.findByUsername(DEFAULT_ADMIN_USERNAME)
                .orElseThrow(() -> new IllegalStateException("Dummy admin user is not seeded"));

        return AdminUserDto.builder()
                .username(admin.getUsername())
                .role(admin.getRole())
                .displayName(admin.getDisplayName())
                .build();
    }
}
