package com.dev.storesystem.domain.services;

import com.dev.storesystem.common.dtos.user.SaveUserDto;
import com.dev.storesystem.common.mappers.EntityMapper;
import com.dev.storesystem.domain.entities.UserEntity;
import com.dev.storesystem.domain.factories.UserFactory;
import com.dev.storesystem.domain.providers.UserProvider;
import com.dev.storesystem.domain.repositories.UserRepository;
import com.dev.storesystem.domain.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(value = SpringExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private EntityMapper mapper;
    @Mock
    private UserProvider provider;
    @Mock
    private PasswordEncryptor encryptor;
    @Mock
    private DateManager dateManager;
    @Mock
    private UserRepository repository;

    @BeforeEach
    void setUp() {
        BDDMockito.when(mapper.fromSaveUserDtoToUserEntity(ArgumentMatchers.any(SaveUserDto.class)))
                .thenReturn(UserFactory.generateActiveUserEntityWithoutId());
        BDDMockito.when(mapper.fromUserEntityToShowUserDto(ArgumentMatchers.any(UserEntity.class)))
                .thenReturn(UserFactory.generateActiveShowUserDto());
        BDDMockito.when(provider.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.empty());
        BDDMockito.when(repository.save(ArgumentMatchers.any(UserEntity.class)))
                .thenReturn(UserFactory.generateActiveUserEntityWithId());
        BDDMockito.doNothing().when(encryptor).encryptUserPassword(ArgumentMatchers.any(UserEntity.class));
        BDDMockito.doNothing().when(dateManager).setSaveTime(ArgumentMatchers.any(UserEntity.class));
    }

    @Test
    void save_AssertUserIsSaved_WhenSuccessful() {
        var saveUser = UserFactory.generateValidSaveUserDto();
        var showUser = service.save(saveUser);
        assertNotNull(showUser);
        assertNotNull(showUser.getId());
        assertEquals(saveUser.getName(), showUser.getName());
        assertEquals(saveUser.getUsername(), showUser.getUsername());
        assertEquals(saveUser.getPermission(), showUser.getPermission().name());
        assertNotNull(showUser.getCreatedAt());
        assertNotNull(showUser.getUpdatedAt());
        assertNull(showUser.getDeletedAt());
    }

    @Test
    void save_AssertThrowsException_WhenUsernameIsAlreadyInUse() {
        BDDMockito.when(provider.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(UserFactory.generateActiveUserEntityWithId()));
        var saveUser = UserFactory.generateValidSaveUserDto();
        assertThrows(UsernameAlreadyInUseException.class, () -> service.save(saveUser));
    }
}