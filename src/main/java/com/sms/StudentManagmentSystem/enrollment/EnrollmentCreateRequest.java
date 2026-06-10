package com.sms.StudentManagmentSystem.enrollment;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentCreateRequest {




    @NotNull(message = "Student id is required")
    private Long studentId;

    @NotNull(message = "Group id is required")
    private Long groupId;





}
