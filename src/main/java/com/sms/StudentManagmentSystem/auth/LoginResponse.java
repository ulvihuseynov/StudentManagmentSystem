package com.sms.StudentManagmentSystem.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private Long userId;
    private String accessToken;
    private String username;
    private String email;
    private List<String> role;

}
