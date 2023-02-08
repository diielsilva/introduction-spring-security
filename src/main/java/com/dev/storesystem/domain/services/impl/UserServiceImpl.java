package com.dev.storesystem.domain.services.impl;

import com.dev.storesystem.common.dtos.user.SaveUserDto;
import com.dev.storesystem.common.dtos.user.ShowUserDto;
import com.dev.storesystem.common.mappers.EntityMapper;
import com.dev.storesystem.domain.exceptions.UsernameInUse;
import com.dev.storesystem.domain.helpers.DateHelper;
import com.dev.storesystem.domain.providers.UserProvider;
import com.dev.storesystem.domain.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserProvider provider;
    private final EntityMapper entityMapper;
    private final PasswordEncoder passwordEncoder;
    private final DateHelper dateHelper;

    public UserServiceImpl(UserProvider provider, EntityMapper entityMapper, PasswordEncoder passwordEncoder, DateHelper dateHelper) {
        this.provider = provider;
        this.entityMapper = entityMapper;
        this.passwordEncoder = passwordEncoder;
        this.dateHelper = dateHelper;
    }

    @Override
    public ShowUserDto save(SaveUserDto saveUserDto) {
        var userEntity = entityMapper.mapFromSaveUserDtoToUserEntity(saveUserDto);
        if (isUsernameInUse(userEntity.getUsername())) {
            throw new UsernameInUse("A identificação de usuário: " + userEntity.getUsername() + " já está em uso!");
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        dateHelper.setSaveTime(userEntity);
        provider.save(userEntity);
        return entityMapper.mapFromUserEntityToShowUserDto(userEntity);
    }

    private boolean isUsernameInUse(String username) {
        return provider.findByUsername(username).isPresent();
    }
}
