package com.sms.StudentManagmentSystem.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Username is required")
    private String username;

    @Email(message = "Email format is invalid")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 6,max = 12, message = "Password must be 6 and 12 characters")
    private String password;

}
