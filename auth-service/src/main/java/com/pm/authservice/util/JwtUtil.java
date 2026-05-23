package com.pm.authservice.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key secretKey;

//    public JwtUtil(@Value("${jwt.secret}") String secret) {
//        if (secret == null || secret.isEmpty()) {
//            throw new IllegalArgumentException("JWT secret cannot be null or empty");
//        }
////        byte[] keyBytes = Base64.getDecoder()
////                .decode(secret.getBytes(StandardCharsets.UTF_8));
//     //   byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);l
//        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
//    }
public JwtUtil(@Value("${jwt.secret}") String secret) {

    if (secret == null || secret.isEmpty()) {
        throw new IllegalArgumentException("JWT secret cannot be null or empty");
    }

    byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);

    this.secretKey = Keys.hmacShaKeyFor(keyBytes);
}
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .subject(email)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *10)) // 10 hours
                .signWith(secretKey)
                .compact();
    }

    public void validateToken(String token) throws SignatureException {
        try{
            Jwts.parser().verifyWith((SecretKey) secretKey)
                    .build()
                    .parseSignedClaims(token);
        } catch (SignatureException e){
            throw new JwtException("Invalid JWT token");
        }catch (JwtException e){
            throw new JwtException("Invalid JWT");
        }
    }
}
