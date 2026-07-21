package com.urlshortener;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${app.secret-key}")
    private String secretKey;

    @Value("${app.default-expiry-time}")
    private Long expiration;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // Generate token
    public String generateToken(String username) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() +expiration);

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expiry)
                .signWith(getSigningKey())
                .compact();
    }

    // Get all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Extract username
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

}