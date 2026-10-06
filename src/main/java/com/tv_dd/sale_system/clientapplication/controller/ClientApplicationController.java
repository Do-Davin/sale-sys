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
        public ResponseEntity<SuccessResponse> getAll() {
                List<ClientApplicationResponse> data = clientApplicationService.getAll().stream()
                                .map(ClientApplicationResponse::from).toList();

                return ResponseEntity.ok(SuccessResponse.builder()
                                .code(HttpStatus.OK)
                                .message("Client applications retrieved successfully")
                                .data(data)
                                .build());
        }

        @GetMapping("/{id}")
        public ResponseEntity<SuccessResponse> getById(@PathVariable Integer id) {
                ClientApplication clientApplication = clientApplicationService.getById(id);

                return ResponseEntity.ok(SuccessResponse.builder()
                                .code(HttpStatus.OK)
                                .message("Client application retrieved successfully")
                                .data(ClientApplicationResponse.from(clientApplication))
                                .build());
        }

        @PostMapping
        public ResponseEntity<SuccessResponse> create(
                        @Valid @RequestBody ClientApplicationRequest request) {
                ClientApplication clientApplication = clientApplicationService.create(request);

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(SuccessResponse.builder()
                                                .code(HttpStatus.CREATED)
                                                .message("Client application created successfully")
                                                .data(ClientApplicationResponse.from(clientApplication))
                                                .build());
        }

        @PutMapping("/{id}")
        public ResponseEntity<SuccessResponse> update(@PathVariable Integer id,
                        @Valid @RequestBody ClientApplicationRequest request) {
                ClientApplication clientApplication = clientApplicationService.update(id, request);

                return ResponseEntity.ok(SuccessResponse.builder()
                                .code(HttpStatus.OK)
                                .message("Client application updated successfully")
                                .data(ClientApplicationResponse.from(clientApplication))
                                .build());
        }
}
