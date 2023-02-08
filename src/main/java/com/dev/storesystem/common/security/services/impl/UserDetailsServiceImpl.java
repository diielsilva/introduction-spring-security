package com.dev.storesystem.common.security.services.impl;

import com.dev.storesystem.domain.providers.UserProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserProvider provider;

    public UserDetailsServiceImpl(UserProvider provider) {
        this.provider = provider;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return provider.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("A identificação de usuário: " + username + " não foi encontrada!"));
    }
}
