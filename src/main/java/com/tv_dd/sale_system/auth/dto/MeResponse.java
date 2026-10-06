package com.tv_dd.sale_system.auth.dto;

import java.util.List;

public record MeResponse(
        Integer userId,
        String username,
        Integer branchId,
        Integer organizationId,
        String organizationName,
        List<AuthenticateResponse.RoleSummary> roles,
        String fullName) {
}
