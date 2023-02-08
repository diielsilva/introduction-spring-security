package com.dev.storesystem.common.security.services;

import java.util.Date;

public interface AuthenticationService {
    String getSecret();

    Long getExpirationTime();

    String generateJwt(String username);

    String getUserFromJwt(String jwt);

    String getTokenPrefix();

    String getAuthorizationHeader();

    Date getExpirationDate();
}
