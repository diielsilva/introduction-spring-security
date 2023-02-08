package com.dev.storesystem.common.security.dtos;

import java.time.OffsetDateTime;

public class AccessTokenDto {
    private String accessToken;
    private OffsetDateTime createdAt;

    public AccessTokenDto() {
    }

    public AccessTokenDto(String accessToken, OffsetDateTime createdAt) {
        this.accessToken = accessToken;
        this.createdAt = createdAt;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
