package com.be001.sale_system.common.security;

public record JwtAuthenticatedUser(
                Integer userId,
                String username,
                Integer branchId,
                String clientAppName) {
}
