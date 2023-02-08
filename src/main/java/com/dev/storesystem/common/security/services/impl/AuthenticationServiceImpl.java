package com.dev.storesystem.common.security.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dev.storesystem.common.security.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Value(value = "${security.jwt.secret}")
    private String secret;
    @Value(value = "${security.jwt.expiration}")
    private Long expirationTime;

    @Override
    public String getSecret() {
        return secret;
    }

    @Override
    public Long getExpirationTime() {
        return expirationTime;
    }

    @Override
    public String generateJwt(String username) {
        return JWT.create().withSubject(username).withExpiresAt(getExpirationDate())
                .sign(Algorithm.HMAC512(secret));
    }

    @Override
    public String getUserFromJwt(String jwt) {
        return JWT.require(Algorithm.HMAC512(secret)).build().verify(jwt.replace(getTokenPrefix(), ""))
                .getSubject();
    }

    @Override
    public String getTokenPrefix() {
        return "Bearer ";
    }

    @Override
    public String getAuthorizationHeader() {
        return "Authorization";
    }

    @Override
    public Date getExpirationDate() {
        return new Date(System.currentTimeMillis() + expirationTime);
    }
}
