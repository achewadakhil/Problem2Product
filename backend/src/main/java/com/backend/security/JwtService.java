package com.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final Key signingKey;
    private final long expiration;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expiration}") long expiration) {
        this.signingKey = Keys.hmacShaKeyFor(resolveSecret(secret));
        this.expiration = expiration;
    }

    public String generateToken(String username) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(username)                          // ✅ FIXED
                .setIssuedAt(Date.from(now))                   // ✅ FIXED
                .setExpiration(Date.from(now.plusMillis(expiration))) // ✅ FIXED
                .signWith(signingKey, SignatureAlgorithm.HS256) // ✅ IMPORTANT
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        Claims claims = extractAllClaims(token);
        return username.equals(claims.getSubject())
                && claims.getExpiration().after(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()                    // ✅ FIXED
                .setSigningKey(signingKey)             // ✅ FIXED
                .build()
                .parseClaimsJws(token)                 // ✅ FIXED
                .getBody();
    }

    private byte[] resolveSecret(String secret) {
        try {
            return Decoders.BASE64.decode(secret);
        } catch (RuntimeException ignored) {
            return secret.getBytes(StandardCharsets.UTF_8);
        }
    }
}