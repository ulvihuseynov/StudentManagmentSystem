package com.sms.StudentManagmentSystem.auth;

import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;


public interface AuthService {


    LoginResponse loginUser(LoginRequest loginRequest);

    ApiMessageResponse registerUser(RegisterRequest registerRequest);

    String currentUser();
}