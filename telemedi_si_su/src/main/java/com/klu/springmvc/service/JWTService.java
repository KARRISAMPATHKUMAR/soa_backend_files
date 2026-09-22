package com.klu.springmvc.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    /*
     * IMPORTANT:
     * This same secret will later be used by
     * Appointment Service to validate the JWT.
     */
    private static final String SECRET_KEY =
            "HealthConnectSecretKeyForJWTAuthentication2026Secure";


    public String generateJWT(
            Map<String, String> userData,
            String role,
            Integer userId) {


        SecretKey secretKey = Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(StandardCharsets.UTF_8)
        );


        Map<String, Object> claims = new HashMap<>();

        claims.put(
                "username",
                userData.get("username")
        );

        claims.put(
                "role",
                role
        );

        claims.put(
                "userId",
                userId
        );


        return Jwts.builder()

                .claims(claims)

                .issuedAt(new Date())

                // JWT valid for 24 hours
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 86400000
                        )
                )

                .signWith(secretKey)

                .compact();
    }
}