package com.sms.StudentManagmentSystem.enrollment;


import com.sms.StudentManagmentSystem.group.Group;
import com.sms.StudentManagmentSystem.student.Student;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentUpdateRequest {




    @NotNull(message = "Student is required")
    private Student student;

    @NotNull(message = "Group is required")
    private Group group;

    @NotNull(message = "Enrollment Date is required")
    private LocalDate enrollment_date;



}
