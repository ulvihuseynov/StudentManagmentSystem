package com.sms.StudentManagmentSystem.auth;

import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {

        LoginResponse loginResponse = authService.loginUser(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiMessageResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {

        ApiMessageResponse registerResponse = authService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.OK).body(registerResponse);
    }

    @GetMapping("/me")
    public ResponseEntity<String> currentUser() {


        String currentUser = authService.currentUser();
        return ResponseEntity.status(HttpStatus.OK).body(currentUser);
    }
}
