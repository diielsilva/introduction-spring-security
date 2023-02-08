package com.dev.storesystem.domain.enums;

import com.dev.storesystem.domain.exceptions.InvalidUserPermission;

public enum UserPermission {
    ROLE_ADMIN,
    ROLE_SELLER;

    public static UserPermission toEnum(String permission) {
        return switch (permission) {
            case "ROLE_ADMIN" -> ROLE_ADMIN;
            case "ROLE_SELLER" -> ROLE_SELLER;
            default -> throw new InvalidUserPermission("A permissão: " + permission + " é inválida!");
        };
    }

}
