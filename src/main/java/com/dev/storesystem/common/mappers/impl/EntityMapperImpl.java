package com.dev.storesystem.common.mappers.impl;

import com.dev.storesystem.common.dtos.user.SaveUserDto;
import com.dev.storesystem.common.dtos.user.ShowUserDto;
import com.dev.storesystem.common.mappers.EntityMapper;
import com.dev.storesystem.domain.entities.UserEntity;
import com.dev.storesystem.domain.enums.UserPermission;
import org.springframework.stereotype.Component;

@Component
public class EntityMapperImpl implements EntityMapper {
    @Override
    public UserEntity mapFromSaveUserDtoToUserEntity(SaveUserDto saveUserDto) {
        var userEntity = new UserEntity();
        userEntity.setName(saveUserDto.getName());
        userEntity.setUsername(saveUserDto.getUsername());
        userEntity.setPassword(saveUserDto.getPassword());
        userEntity.setPermission(UserPermission.toEnum(saveUserDto.getPermission()));
        return userEntity;
    }

    @Override
    public ShowUserDto mapFromUserEntityToShowUserDto(UserEntity userEntity) {
        var showUserDto = new ShowUserDto(userEntity.getId(),
                userEntity.getCreatedAt(), userEntity.getUpdatedAt(), userEntity.getDeletedAt());
        showUserDto.setName(userEntity.getName());
        showUserDto.setUsername(userEntity.getUsername());
        showUserDto.setPermission(userEntity.getPermission());
        return showUserDto;
    }
}
