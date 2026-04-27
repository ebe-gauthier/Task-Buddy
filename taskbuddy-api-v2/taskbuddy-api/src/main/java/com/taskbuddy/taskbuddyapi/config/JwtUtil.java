package com.taskbuddy.taskbuddyapi.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

/**
 * JwtUtil — handles creating and validating JWT tokens.
 *
 * A JWT token is a secure string we give to the user after login.
 * The frontend sends it with every request so the backend knows who is logged in.
 */
@Component
public class JwtUtil {

    // Secret key used to sign tokens — in production store this in env variables
    private static final String SECRET = "taskbuddy-super-secret-key-that-is-long-enough-for-hs256-algorithm";
    private static final long EXPIRATION_MS = 86400000; // 24 hours

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    /** Generate a JWT token for a given user email */
    public String generateToken(String email, String userId) {
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /** Extract the email (subject) from a token */
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    /** Extract the userId claim from a token */
    public String extractUserId(String token) {
        return getClaims(token).get("userId", String.class);
    }

    /** Check if a token is valid and not expired */
    public boolean isValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
