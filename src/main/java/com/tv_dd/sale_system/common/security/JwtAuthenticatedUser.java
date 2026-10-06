package com.tv_dd.sale_system.common.security;

public record JwtAuthenticatedUser(
                Integer userId,
                String username,
                Integer branchId,
                String clientAppName) {
}
