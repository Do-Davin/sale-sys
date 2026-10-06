package com.tv_dd.sale_system.auth.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import com.tv_dd.sale_system.user.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private static final long EXPIRATION_MINUTES = 15;

    private final SecretKey signingKey;

    public JwtService(@Value("${jwt.secret}") String secret) {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(User user) {
        return buildToken(user, null, List.of());
    }

    public String generateToken(User user, String clientAppName, List<String> roleNames) {
        return buildToken(user, clientAppName, roleNames);
    }

    private String buildToken(User user, String clientAppName, List<String> roleNames) {
        Instant now = Instant.now();
        Instant expiry = now.plusSeconds(EXPIRATION_MINUTES * 60);

        var builder = Jwts.builder()
                .subject(String.valueOf(user.getId()))
                .claim("username", user.getUsername())
                .claim("branchId", user.getBranch().getId())
                .claim("roles", roleNames);

        if (clientAppName != null) {
            builder.claim("clientAppName", clientAppName);
        }

        return builder
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(signingKey)
                .compact();
    }

    /**
     * Verifies the token signature and expiration, then returns its claims.
     * Throws an unchecked {@link io.jsonwebtoken.JwtException} (or
     * {@link IllegalArgumentException}) for any invalid, malformed,
     * unsupported, or expired token.
     */
    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
