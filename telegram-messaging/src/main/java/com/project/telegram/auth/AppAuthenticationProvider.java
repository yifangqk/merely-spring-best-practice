package com.project.telegram.auth;

import com.project.telegram.exception.BusinessException;
import com.project.telegram.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class AppAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        AppUserDetails user = (AppUserDetails) userDetailsService.loadUserByUsername(username);
        if (!encoder.matches(password, user.getPassword())) {
            throw BusinessException.of(ExceptionType.ACCOUNT_INVALID_PASSWORD);
        }

        if (user.getLocked()) {
            throw BusinessException.of(ExceptionType.ACCOUNT_LOCKED);
        }

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
