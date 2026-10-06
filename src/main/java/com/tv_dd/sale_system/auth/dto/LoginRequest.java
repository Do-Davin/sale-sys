package com.tv_dd.sale_system.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank String clientAppName) {

}
