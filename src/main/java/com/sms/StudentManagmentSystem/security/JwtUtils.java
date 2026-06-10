package com.sms.StudentManagmentSystem.security;

import com.sms.StudentManagmentSystem.exception.ApiErrorResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {


    private String secretKey;
    private long expiration;

    public String getJwtFromUsername(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();


    }

    public String getUsernameFromToken(String authToken) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(authToken)
                .getPayload().getSubject();


    }

    public boolean validateJwtToken(String authToken) {

        try {
            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(authToken);
            return true;
        } catch (Exception ex) {

            ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                    HttpStatus.BAD_REQUEST.value(), ex.getMessage()
            );

        }
        return false;
    }

    private SecretKey getSignInKey() {

        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
