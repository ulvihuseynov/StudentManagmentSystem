package com.sms.StudentManagmentSystem.student;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class StudentUpdateRequest {


    @NotBlank(message = "First name is required")
    private String firstname;

    @NotBlank(message = "Last name is required")
    private String   lastname;

    @Email
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Phone number is required")
    private String  phone;

    @NotNull(message = "Date birth is required")
    @Past(message = "Date of birth must be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @NotBlank(message = "Status is required")
    private StudentStatus   status;
}
