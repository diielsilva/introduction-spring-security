package com.dev.storesystem.common.security.filters;

import com.dev.storesystem.common.security.dtos.UserCredentialsDto;
import com.dev.storesystem.common.security.handlers.SecurityResponseHandler;
import com.dev.storesystem.common.security.services.AuthenticationService;
import com.dev.storesystem.domain.entities.UserEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationService authService;
    private final AuthenticationManager authManager;
    private final SecurityResponseHandler responseHandler;


    public JwtAuthenticationFilter(AuthenticationService authService, AuthenticationManager authManager,
                                   SecurityResponseHandler responseHandler) {
        this.authService = authService;
        this.authManager = authManager;
        this.responseHandler = responseHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            var mapper = new ObjectMapper();
            var userCredentials = mapper.readValue(request.getInputStream(), UserCredentialsDto.class);
            var springAuth =
                    new UsernamePasswordAuthenticationToken(userCredentials.getUsername(), userCredentials.getPassword());
            return authManager.authenticate(springAuth);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException {
        var username = ((UserEntity) authResult.getPrincipal()).getUsername();
        var jwt = authService.generateJwt(username);
        responseHandler.handleSuccessAuthentication(response, jwt);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        responseHandler.handleSecurityError(response, "Usuário ou senha inválido(a)!",
                request.getServletPath());
    }
}
