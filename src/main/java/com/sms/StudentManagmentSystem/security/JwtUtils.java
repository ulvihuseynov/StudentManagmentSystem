package com.sms.StudentManagmentSystem.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    @Value("${app.jwt.secretKey}")
    private String secretKey;

    @Value("${app.jwt.expirationMs}")
    private long expirationMs;

    public String getJwtFromUsername(UserDetails userDetails) {
        return Jwts.builder().subject(userDetails.getUsername()).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + expirationMs)).signWith(getSignInKey()).compact();


    }

    public String getUsernameFromToken(String authToken) {
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(authToken).getPayload().getSubject();


    }

    public boolean validateJwtToken(String authToken) {

        try {
            Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(authToken);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | SignatureException |
                 IllegalArgumentException e) {
            logger.error("JWT validation error: {}", e.getMessage());
        }
        return false;

    }

    private SecretKey getSignInKey() {

        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
