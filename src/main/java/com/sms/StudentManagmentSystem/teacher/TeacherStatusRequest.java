package com.sms.StudentManagmentSystem.teacher;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherStatusRequest {

    @NotNull(message = "Status is required")
    private TeacherStatus status;
}
