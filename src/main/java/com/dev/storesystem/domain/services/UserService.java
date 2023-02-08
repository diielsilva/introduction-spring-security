package com.dev.storesystem.domain.services;

import com.dev.storesystem.common.dtos.user.SaveUserDto;
import com.dev.storesystem.common.dtos.user.ShowUserDto;

public interface UserService {
    ShowUserDto save(SaveUserDto saveUserDto);
}
