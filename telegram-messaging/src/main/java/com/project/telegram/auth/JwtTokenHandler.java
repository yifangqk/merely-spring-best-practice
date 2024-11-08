package com.project.telegram.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenHandler {
    @Value("${spring.application.name}")
    private String appName;

    @Value("${security.jwt.signing-key}")
    private String signingKey;

    @Value("${security.jwt.expires-in}")
    private Long expiresIn;

    private enum CustomClaims {
        PHONE, FULL_NAME, ACCOUNT_ID, ROLES
    }

    public String issueToken(AppUserDetails userDetails) {
        var issueDate = new Date();
        var expirationDate = new Date(issueDate.getTime() + expiresIn * 1000);
        var roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .issuer(appName)
                .subject(appName)
                .issuedAt(issueDate)
                .expiration(expirationDate)
                .claim(CustomClaims.PHONE.name(), userDetails.getPhone())
                .claim(CustomClaims.FULL_NAME.name(), userDetails.getUsername())
                .claim(CustomClaims.ROLES.name(), roles)
                .signWith(this.getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(this.getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSigningKey() {
        byte[] bytes = Decoders.BASE64.decode(signingKey);
        return Keys.hmacShaKeyFor(bytes);
    }

    String extractPhone(Claims claims) {
        return claims.get(CustomClaims.PHONE.name(), String.class);
    }
}
