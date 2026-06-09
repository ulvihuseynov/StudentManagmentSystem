package com.sms.StudentManagmentSystem.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherResponse {


    private Long teacherId;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String specialization;
    private TeacherStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
