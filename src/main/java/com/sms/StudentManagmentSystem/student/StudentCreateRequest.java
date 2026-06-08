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
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentCreateRequest {


    @NotBlank(message = "First name is required")
    @NotNull
    private String firstname;

    @NotBlank(message = "Last name is required")
    @NotNull
    private String   lastname;

    @Email
    private String email;


//    @Size(min = 7,max = 12,message = "Phone number must be 7 and 12 characters")
@Pattern(regexp = "^(\\+994|0)(50|51|55|70|77|99)\\d{7}$",
        message = "Mobil nömrəni düzgün formatda daxil edin: 0501234567 və ya +994501234567")
    private String  phone;

    @NotNull(message = "Date birth is required")
    @Past(message = "Date of birth must be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    private StudentStatus   status;


}
