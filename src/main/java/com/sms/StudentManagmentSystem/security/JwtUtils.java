package com.sms.StudentManagmentSystem.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {


    private String secretKey;
    private long expiration;

    public String getJwtFromUsername() {
        return Jwts.builder()
                .subject("Ulvi")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();


    }

    public String getUsernameFromToken() {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims("").getPayload().getSubject();


    }

    private SecretKey getSignInKey() {

        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
