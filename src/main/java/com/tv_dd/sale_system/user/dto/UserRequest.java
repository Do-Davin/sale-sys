package com.tv_dd.sale_system.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank(message = "Username is required") String username,
        @NotBlank(message = "Password is required") String password,
        String fullName) {
}
