package com.dev.storesystem.common.security.dtos;

import jakarta.validation.constraints.NotBlank;

public class UserCredentialsDto {
    @NotBlank(message = "A identificação do usuário não pode ser nula!")
    private String username;
    @NotBlank(message = "A senha do usuário não pode ser nula!")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
