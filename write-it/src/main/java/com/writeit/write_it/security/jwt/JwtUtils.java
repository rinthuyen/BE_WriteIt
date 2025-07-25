package com.writeit.write_it.security.jwt;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.access-token.expiration-ms}")
    private int jwtExpirationMs;

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationMs);
        String token = Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getSignInKey(), SIG.HS256)
                .compact();
        log.debug("JWT token generated for user {}, expires at {}", username, expiry);
        return token;
    }

    public boolean isTokenValid(String token, String expectedUsername) {
        try {
            String extractedUsername = extractUsername(token);
            boolean valid = extractedUsername.equals(expectedUsername) && !isTokenExpired(token);
            log.debug("Token validation for user '{}': {}", expectedUsername, valid ? "valid" : "invalid");
            return valid;
        } catch (Exception e) {
            log.warn("JWT token validation failed: {}", e.getClass().getSimpleName());
            log.debug("Detailed error: {}", e.getMessage());
            // avoid exposing potentially sensitive information
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        return resolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.error("Error parsing JWT token: {}", e.getMessage());
            throw e;
            // critical error, best to stop if fail to parse
        }
    }

}
