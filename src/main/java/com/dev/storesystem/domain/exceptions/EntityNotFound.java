package com.dev.storesystem.domain.exceptions;

public class EntityNotFound extends BusinessException {
    public EntityNotFound(String message) {
        super(message);
    }
}
