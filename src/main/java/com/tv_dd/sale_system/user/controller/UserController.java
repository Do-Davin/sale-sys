package com.tv_dd.sale_system.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tv_dd.sale_system.common.dto.SuccessResponse;
import com.tv_dd.sale_system.user.dto.UserRequest;
import com.tv_dd.sale_system.user.dto.UserResponse;
import com.tv_dd.sale_system.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<SuccessResponse> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse response = userService.createUser(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(SuccessResponse.builder()
                        .code(HttpStatus.CREATED)
                        .message("User created successfully")
                        .data(response)
                        .build());
    }
}
