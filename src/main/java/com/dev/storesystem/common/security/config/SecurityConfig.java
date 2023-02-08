package com.dev.storesystem.common.security.config;

import com.dev.storesystem.common.security.filters.JwtAuthenticationFilter;
import com.dev.storesystem.common.security.filters.JwtAuthorizationFilter;
import com.dev.storesystem.common.security.handlers.SecurityResponseHandler;
import com.dev.storesystem.common.security.services.AuthenticationService;
import com.dev.storesystem.domain.exceptions.SecurityConfigException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final AuthenticationService authService;
    private final UserDetailsService detailsService;
    private final SecurityResponseHandler responseHandler;

    public SecurityConfig(AuthenticationService authService, UserDetailsService detailsService,
                          SecurityResponseHandler responseHandler) {
        this.authService = authService;
        this.detailsService = detailsService;
        this.responseHandler = responseHandler;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        return security.csrf().disable().cors().and()
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(requests -> {
                    try {
                        var authManager = security.getSharedObject(AuthenticationConfiguration.class);
                        requests.requestMatchers("**/login").permitAll().anyRequest().authenticated().and()
                                .addFilter(new JwtAuthenticationFilter(
                                        authService, authenticationManager(authManager), responseHandler
                                ))
                                .addFilter(new JwtAuthorizationFilter(
                                        authService, detailsService, responseHandler, authenticationManager(authManager)
                                ));

                    } catch (Exception exception) {
                        throw new SecurityConfigException("Erro ao criar a cadeia de filtros");
                    }
                }).build();
    }
}
