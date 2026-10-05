package com.tv_dd.sale_system.common.dto;

import lombok.Getter;

@Getter
public class SuccessResponse<T> {

    private final String message;
    private final T data;

    private SuccessResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public static <T> SuccessResponse<T> of(String message, T data) {
        return new SuccessResponse<>(message, data);
    }
}
