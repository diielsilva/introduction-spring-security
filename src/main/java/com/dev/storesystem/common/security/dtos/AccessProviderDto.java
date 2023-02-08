package com.dev.storesystem.common.security.dtos;

import java.time.OffsetDateTime;

public class AccessProviderDto {
    private String accessToken;
    private OffsetDateTime issuedAt;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public OffsetDateTime getIssuedAt() {
        return issuedAt;
    }

    public void setIssuedAt(OffsetDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }
}
