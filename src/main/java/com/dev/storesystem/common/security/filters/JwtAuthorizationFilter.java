package com.dev.storesystem.common.security.filters;

import com.dev.storesystem.common.security.handlers.SecurityResponseHandler;
import com.dev.storesystem.common.security.services.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final AuthenticationService authService;
    private final UserDetailsService detailsService;
    private final SecurityResponseHandler responseHandler;

    public JwtAuthorizationFilter(AuthenticationService authService, UserDetailsService detailsService,
                                  SecurityResponseHandler responseHandler, AuthenticationManager authManager) {
        super(authManager);
        this.authService = authService;
        this.detailsService = detailsService;
        this.responseHandler = responseHandler;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException {
        try {
            var springAuth = getSpringAuthFromRequest(request);
            if (springAuth != null) {
                SecurityContextHolder.getContext().setAuthentication(springAuth);
            }
            chain.doFilter(request, response);
        } catch (Exception exception) {
            responseHandler.handleSecurityError(response,
                    "O token é nulo, inválido, está expirado ou você não possui autorização para este recurso!", request.getServletPath());
        }

    }

    private UsernamePasswordAuthenticationToken getSpringAuthFromRequest(HttpServletRequest request) {
        var jwt = request.getHeader(authService.getAuthorizationHeader());
        if (jwt == null || !jwt.startsWith(authService.getTokenPrefix())) {
            return null;
        }
        var username = authService.getUserFromJwt(jwt);
        if (username == null) {
            return null;
        }
        var user = detailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
    }
}
