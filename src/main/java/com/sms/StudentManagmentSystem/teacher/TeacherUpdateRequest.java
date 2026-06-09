package com.sms.StudentManagmentSystem.teacher;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherUpdateRequest {

    @NotBlank(message = "First name is required")
    private String firstname;

    @NotBlank(message = "Last name is required")
    private String lastname;

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(\\+994|0)(50|51|55|70|77|99)\\d{7}$",
            message = "Mobil nömrəni düzgün formatda daxil edin: 0501234567 və ya +994501234567")
    private String phone;

    @NotBlank(message = "Specialization is required")
    private String specialization;
}
