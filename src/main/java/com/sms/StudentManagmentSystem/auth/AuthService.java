package com.sms.StudentManagmentSystem.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public LoginResponse loginUser(LoginRequest loginRequest) {
    }
}
