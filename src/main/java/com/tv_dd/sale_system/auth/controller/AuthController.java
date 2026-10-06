package com.tv_dd.sale_system.auth.controller;

import com.tv_dd.sale_system.auth.dto.AuthenticateResponse;
import com.tv_dd.sale_system.auth.dto.LoginRequest;
import com.tv_dd.sale_system.auth.dto.MeResponse;
import com.tv_dd.sale_system.auth.service.AuthService;
import com.tv_dd.sale_system.auth.service.JwtService;
import com.tv_dd.sale_system.common.dto.SuccessResponse;
import com.tv_dd.sale_system.common.security.JwtAuthenticatedUser;
import com.tv_dd.sale_system.user.model.User;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/authenticate")
    public ResponseEntity<SuccessResponse> login(@Valid @RequestBody LoginRequest request) {
        User user = authService.authenticate(request);
        List<String> roleNames = authService.resolveEnabledRoleNames(user, request.clientAppName());
        String accessToken = jwtService.generateToken(user, request.clientAppName(), roleNames);
        AuthenticateResponse data = authService.buildAuthenticateResponse(user, accessToken);

        return ResponseEntity.ok(SuccessResponse.builder()
                .code(HttpStatus.OK)
                .message("Login successful")
                .data(data)
                .build());
    }

    @GetMapping("/me")
    public ResponseEntity<SuccessResponse> me(@AuthenticationPrincipal JwtAuthenticatedUser principal) {
        MeResponse data = authService.buildMeResponse(principal.userId());

        return ResponseEntity.ok(SuccessResponse.builder()
                .code(HttpStatus.OK)
                .message("Current user retrieved successfully")
                .data(data)
                .build());
    }

}
