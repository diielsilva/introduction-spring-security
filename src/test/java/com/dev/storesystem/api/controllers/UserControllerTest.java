package com.dev.storesystem.api.controllers;

import com.dev.storesystem.common.dtos.user.SaveUserDto;
import com.dev.storesystem.domain.factories.UserFactory;
import com.dev.storesystem.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(value = SpringExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController controller;
    @Mock
    private UserService service;

    @BeforeEach
    void setUp() {
        BDDMockito.when(service.save(ArgumentMatchers.any(SaveUserDto.class)))
                .thenReturn(UserFactory.generateActiveShowUserDto());
    }

    @Test
    void save_AssertGetUser_WhenSuccessful() {
        var saveUser = UserFactory.generateValidSaveUserDto();
        var response = controller.save(saveUser);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(saveUser.getName(), response.getBody().getName());
        assertEquals(saveUser.getUsername(), response.getBody().getUsername());
        assertEquals(saveUser.getPermission(), response.getBody().getPermission().name());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNull(response.getBody().getDeletedAt());
    }
}