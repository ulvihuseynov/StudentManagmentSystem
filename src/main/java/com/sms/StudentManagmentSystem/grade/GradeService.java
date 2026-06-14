package com.sms.StudentManagmentSystem.grade;

import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface GradeService {
    GradeResponse createGrade(@Valid GradeCreateRequest gradeCreateRequest);

    List<GradeResponse> getGradeByStudentId(Long studentId);

    List<GradeResponse> getGradeByEnrollmentId(Long enrollmentId);

    GradeResponse updateGrade(@Valid GradeUpdateRequest gradeUpdateRequest, Long id);

  GradeMaxScoreResponse getGradeByEnrollmentIdSummary(Long enrollmentId);

    ApiMessageResponse deleteGrade(Long id);

    List<GradeResponse> getMyGrades();
}


