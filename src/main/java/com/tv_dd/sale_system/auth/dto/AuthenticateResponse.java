package com.tv_dd.sale_system.auth.dto;

import java.util.List;
import com.tv_dd.sale_system.role.model.Role;

public record AuthenticateResponse(
        String accessToken,
        Integer userId,
        Integer branchId,
        Integer organizationId,
        String organizationName,
        List<RoleSummary> roles,
        String fullName) {

    public record RoleSummary(Integer roleId, String roleName) {

        public static RoleSummary from(Role role) {
            return new RoleSummary(role.getId(), role.getName());
        }
    }
}
