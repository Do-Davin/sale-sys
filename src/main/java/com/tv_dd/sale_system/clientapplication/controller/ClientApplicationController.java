package com.tv_dd.sale_system.clientapplication.controller;

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
import com.tv_dd.sale_system.clientapplication.dto.ClientApplicationRequest;
import com.tv_dd.sale_system.clientapplication.dto.ClientApplicationResponse;
import com.tv_dd.sale_system.clientapplication.model.ClientApplication;
import com.tv_dd.sale_system.clientapplication.service.ClientApplicationService;
import com.tv_dd.sale_system.common.dto.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/client-application")
@RequiredArgsConstructor
public class ClientApplicationController {

    private final ClientApplicationService clientApplicationService;

    @GetMapping
    public ResponseEntity<SuccessResponse<List<ClientApplicationResponse>>> getAll() {
        List<ClientApplicationResponse> data = clientApplicationService.getAll().stream()
                .map(ClientApplicationResponse::from).toList();

        return ResponseEntity.ok(SuccessResponse.of("Client applications retrieved successfully", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<ClientApplicationResponse>> getById(@PathVariable Integer id) {
        ClientApplication clientApplication = clientApplicationService.getById(id);

        return ResponseEntity.ok(SuccessResponse.of("Client application retrieved successfully",
                ClientApplicationResponse.from(clientApplication)));
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<ClientApplicationResponse>> create(
            @Valid @RequestBody ClientApplicationRequest request) {
        ClientApplication clientApplication = clientApplicationService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(SuccessResponse.of("Client application created successfully",
                        ClientApplicationResponse.from(clientApplication)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<ClientApplicationResponse>> update(@PathVariable Integer id,
            @Valid @RequestBody ClientApplicationRequest request) {
        ClientApplication clientApplication = clientApplicationService.update(id, request);

        return ResponseEntity.ok(SuccessResponse.of("Client application updated successfully",
                ClientApplicationResponse.from(clientApplication)));
    }
}
