package com.sms.StudentManagmentSystem.grade;

import jakarta.validation.Valid;

import java.util.List;

public interface GradeService {
    GradeResponse createGrade(@Valid GradeCreateRequest gradeCreateRequest);

    GradeResponse getGradeByStudentId(Long studentId);

   GradeResponse getGradeByEnrollmentId(Long enrollmentId);

    GradeResponse updateGrade(@Valid GradeUpdateRequest gradeUpdateRequest, Long id);
}
