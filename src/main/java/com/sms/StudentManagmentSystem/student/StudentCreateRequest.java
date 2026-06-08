package com.sms.StudentManagmentSystem.student;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentCreateRequest {


    @NotBlank(message = "First name is required")
    private String firstname;

    @NotBlank(message = "Last name is required")
    private String   lastname;

    @Email
    @NotBlank(message = "Email is required")
    private String email;


    @Pattern(regexp = "^(\\+994|0)(50|51|55|70|77|99)\\d{7}$",
        message = "Mobil nömrəni düzgün formatda daxil edin: 0501234567 və ya +994501234567")
    @NotBlank(message = "Phone number is required")
    private String  phone;

    @NotNull(message = "Date birth is required")
    @Past(message = "Date of birth must be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;


}
