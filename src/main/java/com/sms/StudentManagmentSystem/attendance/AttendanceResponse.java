package com.sms.StudentManagmentSystem.attendance;


import com.sms.StudentManagmentSystem.enrollment.EnrollmentResponse;
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
public class AttendanceResponse {


    private Long attendanceId;
    private LocalDate lessonDate;
    private AttendanceStatus status;
    private String note;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private EnrollmentResponse enrollment;
}
