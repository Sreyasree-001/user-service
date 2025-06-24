package com.code.mvc.security.jwt;

import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;


@Component
public class TokenProvider {
    //ensures only one logger instance per class
    //the parameter tells the logger which class it is logging for
    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    @Value("${jwt.secret}")
    private String jwtSecret; // Secret Key
    @Value("${jwt.expiration}")
    private int jwtExpiration; // Expiration in Seconds
    @Value("${jwt.refreshExpiration}")
    private int jwtRefreshExpiration; // RefreshToken Expiration in Seconds

    // Secret Key Generator
    private SecretKey getJWTSecretKey(String jwtSecret){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
}
