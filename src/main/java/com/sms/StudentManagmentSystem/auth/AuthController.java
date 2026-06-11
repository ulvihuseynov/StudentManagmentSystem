package com.sms.StudentManagmentSystem.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest){

       LoginResponse loginResponse= authService.loginUser(loginRequest);
       return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @PostMapping
    public ResponseEntity<LoginResponse> registerUser(@RequestBody RegisterRequest registerRequest){

        LoginResponse loginResponse= authService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }
}
