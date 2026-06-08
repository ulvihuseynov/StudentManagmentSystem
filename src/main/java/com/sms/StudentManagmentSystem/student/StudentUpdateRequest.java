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
    @NotNull
    private String firstname;

    @NotBlank(message = "Last name is required")
    @NotNull
    private String   lastname;

    @Email
    private String email;

//    @Min(value = 7,message = "Phone number must be at least 7 characters")
//    @Max(value = 12,message = "Phone number must be at most 12 characters")
    private String  phone;

    @NotNull(message = "Date birth is required")
    @Past(message = "Date of birth must be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private StudentStatus   status;
}
