package com.sms.StudentManagmentSystem.student;



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
public class StudentResponse {

    private Long  studentId;
    private String firstname;
    private String   lastname;
    private String email;
    private String  phone;
    private LocalDate dateOfBirth;
    private StudentStatus   status;
    private LocalDate createdAt;
    private LocalDate     updatedAt;
}
