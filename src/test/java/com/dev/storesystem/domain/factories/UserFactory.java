package com.dev.storesystem.domain.factories;

import com.dev.storesystem.common.dtos.user.SaveUserDto;
import com.dev.storesystem.common.dtos.user.ShowUserDto;
import com.dev.storesystem.domain.entities.UserEntity;
import com.dev.storesystem.domain.enums.UserPermission;

import java.time.OffsetDateTime;

public abstract class UserFactory {
    // Methods To Create UserEntities
    // ----------------------------------------------------------------------------------------------------------------
    // Users Without IDS
    public static UserEntity generateActiveUserEntityWithoutId() {
        var user = new UserEntity();
        user.setName("Daniel Silva");
        user.setUsername("diel");
        user.setPassword("12345");
        user.setPermission(UserPermission.ROLE_ADMIN);
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());
        return user;
    }

    public static UserEntity generateInactiveUserEntityWithoutId() {
        var user = new UserEntity();
        user.setName("Daniel Silva");
        user.setUsername("diel");
        user.setPassword("12345");
        user.setPermission(UserPermission.ROLE_ADMIN);
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());
        user.setDeletedAt(OffsetDateTime.now());
        return user;
    }

    public static UserEntity generateEmptyUserEntity() {
        return new UserEntity();
    }

    // ----------------------------------------------------------------------------------------------------------------
    // Users With IDS
    public static UserEntity generateActiveUserEntityWithId() {
        var user = new UserEntity();
        user.setId(1L);
        user.setName("Daniel Silva");
        user.setUsername("diel");
        user.setPassword("12345");
        user.setPermission(UserPermission.ROLE_ADMIN);
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());
        return user;
    }

    public static UserEntity generateInactiveUserEntityWithId() {
        var user = new UserEntity();
        user.setId(1L);
        user.setName("Daniel Silva");
        user.setUsername("diel");
        user.setPassword("12345");
        user.setPermission(UserPermission.ROLE_ADMIN);
        user.setCreatedAt(OffsetDateTime.now());
        user.setUpdatedAt(OffsetDateTime.now());
        user.setDeletedAt(OffsetDateTime.now());
        return user;
    }

    // ----------------------------------------------------------------------------------------------------------------
    // Methods To Create UsersDto
    public static SaveUserDto generateValidSaveUserDto() {
        var dto = new SaveUserDto();
        dto.setName("Daniel Silva");
        dto.setUsername("diel");
        dto.setPassword("12345");
        dto.setPermission("ROLE_ADMIN");
        return dto;
    }

    public static SaveUserDto generateSaveUserDtoWithInvalidPermission() {
        var dto = new SaveUserDto();
        dto.setName("Daniel Silva");
        dto.setUsername("diel");
        dto.setPassword("12345");
        dto.setPermission("ROLE_ADMINS");
        return dto;
    }

    public static SaveUserDto generateEmptySaveUserDto() {
        return new SaveUserDto();
    }

    public static ShowUserDto generateActiveShowUserDto() {
        var dto = new ShowUserDto();
        dto.setId(1L);
        dto.setName("Daniel Silva");
        dto.setUsername("diel");
        dto.setPermission(UserPermission.ROLE_ADMIN);
        dto.setCreatedAt(OffsetDateTime.now());
        dto.setUpdatedAt(OffsetDateTime.now());
        return dto;
    }
}
