package com.learn.quizapp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    // ⚠️ In production, move this to application.yml
    private static final Key SECRET_KEY =
            Keys.hmacShaKeyFor(
                    "this-is-my-secret-key-for-jwt-and-it-is-very-long"
                            .getBytes()
            );

    // 10 minutes
    private static final long EXPIRATION_TIME = 10 * 60 * 1000;

    /* ===================== TOKEN GENERATION ===================== */

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                )
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /* ===================== TOKEN VALIDATION ===================== */

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserName(token);
        return (username.equals(userDetails.getUsername())
                && !isTokenExpired(token));
    }

    /* ===================== EXTRACT USERNAME ===================== */

    public String getUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /* ===================== HELPER METHODS ===================== */

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration)
                .before(new Date());
    }

    private <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
