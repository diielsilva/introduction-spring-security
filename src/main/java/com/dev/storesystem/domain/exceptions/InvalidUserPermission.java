package com.dev.storesystem.domain.exceptions;

public class InvalidUserPermission extends BusinessException {
    public InvalidUserPermission(String message) {
        super(message);
    }
}
