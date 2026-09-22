package com.klu.springmvc.service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

    /*
     * MUST BE THE SAME SECRET USED
     * IN THE PATIENT SERVICE.
     */
    private static final String SECRET_KEY =
            "HealthConnectSecretKeyForJWTAuthentication2026Secure";


    public Map<String, String> validateJWT(
            String token) throws Exception {

        SecretKey secretKey =
                Keys.hmacShaKeyFor(
                        SECRET_KEY.getBytes(
                                StandardCharsets.UTF_8)
                );


        Claims claims =
                Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();


        Map<String, String> parsedJWT =
                new HashMap<>();


        parsedJWT.put(
                "username",
                claims.get("username").toString()
        );


        parsedJWT.put(
                "role",
                claims.get("role").toString()
        );


        parsedJWT.put(
                "id",
                claims.get("userId").toString()
        );


        return parsedJWT;
    }
}