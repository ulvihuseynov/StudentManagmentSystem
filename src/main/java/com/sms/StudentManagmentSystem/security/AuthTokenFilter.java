package com.sms.StudentManagmentSystem.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = jwtToken(request);



    }
    
    private String jwtToken(HttpServletRequest request){

        String header = request.getHeader("Authorization");
        if (header !=null && header.startsWith("Bearer ")){
            return header.substring(7);
        }
        return null;
    }
}
