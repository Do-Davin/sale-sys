package com.tv_dd.sale_system.clientapplication.dto;

import com.tv_dd.sale_system.clientapplication.model.ClientApplication;
import lombok.Getter;

@Getter
public class ClientApplicationResponse {

    private final Integer id;
    private final String name;
    private final Boolean enable;

    private ClientApplicationResponse(
            Integer id,
            String name,
            Boolean enable) {
        this.id = id;
        this.name = name;
        this.enable = enable;
    }

    public static ClientApplicationResponse from(ClientApplication clientApplication) {
        return new ClientApplicationResponse(
                clientApplication.getId(),
                clientApplication.getName(),
                clientApplication.getEnable());
    }
}
