package com.tv_dd.sale_system.clientapplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientApplicationRequest(
                @NotBlank String name,
                @NotNull Boolean enable) {

}
