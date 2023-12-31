package com.trading212.code212.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;


@Service
public class JWTService {

    private final String SECRET_KEY = "trading212_ewfkweofwep72139y1283_trading2=912=932121i0dkwqo_20ei32909";

    public String issueToken(String subject, Long userId) {
        return issueToken(subject, userId, Map.of());
    }

    public String issueToken(String subject, Long userId, String ...scopes) {
        return issueToken(subject, userId, Map.of("scopes", scopes));
    }

    public String issueToken(String subject, Long userId, List<String> scopes) {
        return issueToken(subject, userId, Map.of("scopes", scopes));
    }

    public String issueToken(
            String subject,
            Long userId,
            Map<String, Object> claims) {

        Map<String, Object> modifiableClaims = new HashMap<>(claims);
        modifiableClaims.put("userId", userId);

        String token = Jwts.builder()
                .setClaims(modifiableClaims)
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(
                        Date.from(Instant.now().plus(1, DAYS))
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public boolean isTokenValid(String jwt, String username) {
        String subject = getSubject(jwt);
        return subject.equals(username) && !isTokenExpired(jwt);
    }

    private boolean isTokenExpired(String jwt) {
        Date today = Date.from(Instant.now());
        return getClaims(jwt).getExpiration().before(today);
    }
}
