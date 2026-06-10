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
public class EnrollmentUpdateStatus {

    @NotNull(message = "Status is required")
    private EnrollmentStatus status;

}
