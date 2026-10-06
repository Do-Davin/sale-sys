package com.tv_dd.sale_system.common.exception;

import com.tv_dd.sale_system.common.dto.SuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<SuccessResponse> handleBadCredentials(BadCredentialsException ex) {
        log.warn("Authentication failed: {}", ex.getMessage());

        SuccessResponse body = SuccessResponse.builder()
                .code(HttpStatus.UNAUTHORIZED)
                .message("Invalid username or password")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<SuccessResponse> handleDisabled(DisabledException ex) {
        log.warn("Access denied: {}", ex.getMessage());

        SuccessResponse body = SuccessResponse.builder()
                .code(HttpStatus.FORBIDDEN)
                .message("Account is disabled")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<SuccessResponse> handleAccessDenied(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());

        SuccessResponse body = SuccessResponse.builder()
                .code(HttpStatus.FORBIDDEN)
                .message("Access denied")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<SuccessResponse> handleValidation(MethodArgumentNotValidException ex) {
        log.warn("Validation failed: {}", ex.getMessage());

        SuccessResponse body = SuccessResponse.builder()
                .code(HttpStatus.BAD_REQUEST)
                .message("Validation failed")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<SuccessResponse> handleResponseStatus(ResponseStatusException ex) {
        HttpStatusCode status = ex.getStatusCode();
        String reason = ex.getReason() != null ? ex.getReason() : status.toString();

        log.warn("Request rejected: status={} reason={}", status, reason);

        SuccessResponse body = SuccessResponse.builder()
                .code(status)
                .message(reason)
                .data(null)
                .build();

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<SuccessResponse> handleGeneric(Exception ex) {
        log.error("Unexpected server error", ex);

        SuccessResponse body = SuccessResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Internal server error")
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
