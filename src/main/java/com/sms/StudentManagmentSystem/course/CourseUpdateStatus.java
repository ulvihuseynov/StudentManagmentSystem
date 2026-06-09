package com.sms.StudentManagmentSystem.course;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateStatus {

    @NotNull(message = "Status is required")
    private CourseStatus status;
}
