package com.tv_dd.sale_system.role.dto;

import com.tv_dd.sale_system.clientapplication.model.ClientApplication;
import com.tv_dd.sale_system.role.model.Role;
import lombok.Getter;

@Getter
public class RoleResponse {

    private final Integer id;
    private final String name;
    private final Boolean enable;
    private final Integer clientApplicationId;
    private final String clientApplicationName;

    private RoleResponse(
            Integer id,
            String name,
            Boolean enable,
            Integer clientApplicationId,
            String clientApplicationName) {
        this.id = id;
        this.name = name;
        this.enable = enable;
        this.clientApplicationId = clientApplicationId;
        this.clientApplicationName = clientApplicationName;
    }

    public static RoleResponse from(Role role) {
        ClientApplication clientApplication = role.getClientApplication();
        Integer clientApplicationId = clientApplication != null ? clientApplication.getId() : null;
        String clientApplicationName = clientApplication != null ? clientApplication.getName() : null;

        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getEnable(),
                clientApplicationId,
                clientApplicationName);
    }
}
