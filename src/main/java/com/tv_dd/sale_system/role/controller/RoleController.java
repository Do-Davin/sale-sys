package com.tv_dd.sale_system.role.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.tv_dd.sale_system.common.dto.SuccessResponse;
import com.tv_dd.sale_system.role.dto.RoleRequest;
import com.tv_dd.sale_system.role.dto.RoleResponse;
import com.tv_dd.sale_system.role.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<SuccessResponse> getAll() {
        List<RoleResponse> data = roleService.getAll();

        return ResponseEntity.ok(SuccessResponse.builder()
                .code(HttpStatus.OK)
                .message("Roles retrieved successfully")
                .data(data)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getById(@PathVariable Integer id) {
        RoleResponse role = roleService.getById(id);

        return ResponseEntity.ok(SuccessResponse.builder()
                .code(HttpStatus.OK)
                .message("Role retrieved successfully")
                .data(role)
                .build());
    }

    @PostMapping
    public ResponseEntity<SuccessResponse> create(@Valid @RequestBody RoleRequest request) {
        RoleResponse role = roleService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.builder()
                        .code(HttpStatus.CREATED)
                        .message("Role created successfully")
                        .data(role)
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> update(@PathVariable Integer id,
            @Valid @RequestBody RoleRequest request) {
        RoleResponse role = roleService.update(id, request);

        return ResponseEntity.ok(SuccessResponse.builder()
                .code(HttpStatus.OK)
                .message("Role updated successfully")
                .data(role)
                .build());
    }
}
