package ru.gee.learning.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.gee.learning.entity.User;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    public String getUserNameFromToken(String authToken) {
        return getClaimsFromToken(authToken)
                .getSubject();
    }

    public Claims getClaimsFromToken(String authToken) {
        String key = Base64.getEncoder().encodeToString(secret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(authToken)
                .getBody();
    }

    public boolean validateToken(String authToken, UserDetails userDetails) {
        return getUserNameFromToken(authToken)
                .equals(userDetails.getUsername()) &&
                getClaimsFromToken(authToken)
                        .getExpiration()
                        .after(new Date());
    }

    public String generateToken(User user) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", List.of(user.getRole()));

        long expirationMs = Long.parseLong(expiration);
        Date issueAt = new Date();
        Date expirationTo = new Date(issueAt.getTime() + expirationMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(issueAt)
                .setExpiration(expirationTo)
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

}
