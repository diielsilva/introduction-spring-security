package com.dev.storesystem.domain.exceptions;

public class SecurityConfigException extends RuntimeException {
    private final String message;

    public SecurityConfigException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
