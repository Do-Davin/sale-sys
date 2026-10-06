package com.tv_dd.sale_system.common.dto;

import org.springframework.http.HttpStatusCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.annotation.JsonSerialize;

@Data
@Builder
@AllArgsConstructor
public class SuccessResponse {

    @JsonSerialize(using = HttpStatusCodeSerializer.class)
    private HttpStatusCode code;
    private String message;
    private Object data;
}
