package com.sms.StudentManagmentSystem.enrollment;

import jakarta.validation.Valid;

import java.util.List;

public interface EnrollmentService {
    EnrollmentResponse createEnrollment(@Valid EnrollmentCreateRequest enrollmentCreateRequest);

    List<EnrollmentResponse> getAllEnrollment();

    List<EnrollmentResponse> getEnrollmentByStudent(Long studentId);

    List<EnrollmentResponse> getEnrollmentByGroup(Long groupId);

    EnrollmentResponse getEnrollmentStatusUpdate(Long id);

    EnrollmentResponse deactiveEnrollment(Long id);
}
