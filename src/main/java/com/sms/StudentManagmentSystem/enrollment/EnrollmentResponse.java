package com.sms.StudentManagmentSystem.enrollment;


import com.sms.StudentManagmentSystem.group.GroupResponse;
import com.sms.StudentManagmentSystem.student.StudentResponse;
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

    private LocalDate enrollmentDate;

    private EnrollmentStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
