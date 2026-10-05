package com.tv_dd.sale_system.user.dto;

import com.tv_dd.sale_system.user.model.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private final Integer id;
    private final String username;
    private final String fullName;

    public UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.fullName = user.getFullName();
    }
}
