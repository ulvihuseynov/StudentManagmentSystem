package com.sms.StudentManagmentSystem.attendance;


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
public class AttendanceUpdateRequest {

    @NotNull(message = "Lesson date is required")
    private LocalDate lessonDate;

    @NotNull(message = "Status is required")
    private AttendanceStatus status;

    @NotNull(message = "Note is required")
    private String note;

}
