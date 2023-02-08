package com.dev.storesystem.common.mappers;

import com.dev.storesystem.common.dtos.user.SaveUserDto;
import com.dev.storesystem.common.dtos.user.ShowUserDto;
import com.dev.storesystem.domain.entities.UserEntity;

public interface EntityMapper {
    UserEntity mapFromSaveUserDtoToUserEntity(SaveUserDto saveUserDto);
    ShowUserDto mapFromUserEntityToShowUserDto(UserEntity userEntity);
}
