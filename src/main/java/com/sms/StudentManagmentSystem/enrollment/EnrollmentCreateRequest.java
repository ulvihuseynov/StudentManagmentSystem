package com.sms.StudentManagmentSystem.enrollment;


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
public class EnrollmentCreateRequest {




    @NotNull(message = "Student id is required")
    private Long studentId;

    @NotNull(message = "Group id is required")
    private Long groupId;



    @NotNull(message = "Enrollment date id is required")
    private LocalDate enrollmentDate;




}
