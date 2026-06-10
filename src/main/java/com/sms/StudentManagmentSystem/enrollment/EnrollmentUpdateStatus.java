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
public class EnrollmentUpdateStatus {

    @NotNull(message = "Status is required")
    private EnrollmentStatus status;

}
