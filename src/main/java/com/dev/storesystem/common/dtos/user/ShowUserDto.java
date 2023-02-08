package com.dev.storesystem.common.dtos.user;

import com.dev.storesystem.common.dtos.ShowEntityDto;
import com.dev.storesystem.domain.enums.UserPermission;

import java.time.OffsetDateTime;

public class ShowUserDto extends ShowEntityDto {
    private String name;
    private String username;
    private UserPermission permission;

    public ShowUserDto(Long id, OffsetDateTime createdAt, OffsetDateTime updatedAt, OffsetDateTime deletedAt) {
        super(id, createdAt, updatedAt, deletedAt);
    }

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

    public UserPermission getPermission() {
        return permission;
    }

    public void setPermission(UserPermission permission) {
        this.permission = permission;
    }
}
