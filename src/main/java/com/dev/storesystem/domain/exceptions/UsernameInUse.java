package com.dev.storesystem.domain.exceptions;

public class UsernameInUse extends BusinessException{
    public UsernameInUse(String message) {
        super(message);
    }
}
