package com.sms.StudentManagmentSystem.attendance;

import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface AttendanceService {
    AttendanceResponse createAttendance(@Valid AttendanceCreateRequest attendanceCreateRequest);

    List<AttendanceResponse> attendanceByStudentId(Long studentId);

    List<AttendanceResponse> attendanceByEnrollmentId(Long enrollmentId);

    AttendanceResponse updateAttendance(@Valid AttendanceUpdateRequest attendanceUpdateRequest, Long attendanceId);

    ApiMessageResponse deleteAttendance(Long attendanceId);

    List<AttendanceResponse> getMyGAttendances();
}
