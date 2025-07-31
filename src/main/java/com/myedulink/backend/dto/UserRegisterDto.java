package com.myedulink.backend.dto;

import com.myedulink.backend.model.User;

public class UserRegisterDto {
    private String id;
    private String email;

    public UserRegisterDto(User user) {
        this.id = user.getId().toString();
        this.email = user.getEmail();
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
