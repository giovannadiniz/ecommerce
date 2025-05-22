package com.gec.ecommerce.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String SECRET = "minhaChaveJWTsuperSecreta123"; // use uma env var no mundo real

    public String generateToken(String email) {
        long expiration = 1000 * 60 * 60 * 10; // 10h
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}
