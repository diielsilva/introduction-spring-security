package com.dev.storesystem.domain.providers;

import com.dev.storesystem.domain.entities.UserEntity;

import java.util.Optional;

public interface UserProvider extends AbstractProvider<UserEntity> {
    Optional<UserEntity> findByUsername(String username);
}
