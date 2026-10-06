package com.tv_dd.sale_system.common.security;

import java.io.IOException;
import java.util.List;
import com.tv_dd.sale_system.auth.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * Reads a Bearer JWT (if present), validates it, and populates the Spring
 * Security context with an authenticated {@link JwtAuthenticatedUser}.
 *
 * No token → the chain simply continues and Spring Security's
 * authorization rules decide whether the route is public or protected.
 * An invalid/expired token never results in an authenticated context.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith(BEARER_PREFIX)) {
            String token = header.substring(BEARER_PREFIX.length());

            try {
                Claims claims = jwtService.parseClaims(token);

                JwtAuthenticatedUser principal = new JwtAuthenticatedUser(
                        Integer.valueOf(claims.getSubject()),
                        claims.get("username", String.class),
                        claims.get("branchId", Integer.class),
                        claims.get("clientAppName", String.class));

                List<?> roleClaim = claims.get("roles", List.class);
                List<GrantedAuthority> authorities = roleClaim == null
                        ? List.of()
                        : roleClaim.stream()
                                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + role))
                                .toList();

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        principal, null, authorities);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException | IllegalArgumentException ex) {
                // Invalid, malformed, unsupported, or expired token: leave the
                // context unauthenticated and let Spring Security reject the
                // request if the route requires authentication.
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }
}
