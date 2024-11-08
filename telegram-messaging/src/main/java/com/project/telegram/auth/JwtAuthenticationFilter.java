package com.project.telegram.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER = "Bearer ";
    private final JwtTokenHandler jwtTokenHandler;
    private final AppUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("Filter with {}, {}", request.getRequestURI(), request.getMethod());
        // Extract the token from the HTTP Authorization header
        var token = Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .map(hd -> hd.substring(BEARER.length()))
                .map(String::trim)
                .orElse(StringUtils.EMPTY);


        if (!token.isEmpty() ) {
            this.validateTokenAndSetAuthentication(request, token);
        }

        filterChain.doFilter(request, response);
    }

    private void validateTokenAndSetAuthentication(HttpServletRequest request, String token) {
        try {
            var claims = jwtTokenHandler.extractClaims(token);
            var phoneNumber = jwtTokenHandler.extractPhone(claims);
            var userDetails = userDetailsService.loadUserByUsername(phoneNumber);

            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.warn("Invalid JWT token, cannot assign authentication to security context: {}", e.getMessage());
        }
    }
}
