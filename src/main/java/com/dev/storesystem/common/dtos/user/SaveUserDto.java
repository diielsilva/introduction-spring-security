package com.dev.storesystem.common.dtos.user;

import jakarta.validation.constraints.NotBlank;

public class SaveUserDto {
    @NotBlank(message = "O nome do usuário não pode ser nulo!")
    private String name;
    @NotBlank(message = "A identificação do usuário não pode ser nula!")
    private String username;
    @NotBlank(message = "A senha do usuário não pode ser nula!")
    private String password;
    @NotBlank(message = "A permissão do usuário não pode ser nula!")
    private String permission;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
