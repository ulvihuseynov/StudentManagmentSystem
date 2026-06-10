package com.sms.StudentManagmentSystem.enrollment;


import com.sms.StudentManagmentSystem.group.Group;
import com.sms.StudentManagmentSystem.group.GroupResponse;
import com.sms.StudentManagmentSystem.student.Student;
import com.sms.StudentManagmentSystem.student.StudentResponse;
import jakarta.persistence.*;
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
public class EnrollmentResponse {


    private Long enrollmentId;

    private StudentResponse student;

    private GroupResponse group;

    private LocalDate enrollment_date;

    private EnrollmentStatus status;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
