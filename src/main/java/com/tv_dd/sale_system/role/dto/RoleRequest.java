package com.tv_dd.sale_system.role.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoleRequest(
        @NotBlank String name,
        @NotNull Boolean enable,
        Integer clientApplicationId) {
}
