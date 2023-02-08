package com.dev.storesystem.api.controllers;

import com.dev.storesystem.common.dtos.user.SaveUserDto;
import com.dev.storesystem.common.dtos.user.ShowUserDto;
import com.dev.storesystem.domain.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PreAuthorize(value = "hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ShowUserDto> save(@RequestBody @Valid SaveUserDto saveUserDto) {
        var user = service.save(saveUserDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
