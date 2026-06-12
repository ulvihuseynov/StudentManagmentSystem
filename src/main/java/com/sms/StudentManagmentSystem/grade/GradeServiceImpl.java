package com.sms.StudentManagmentSystem.grade;


import com.sms.StudentManagmentSystem.enrollment.Enrollment;
import com.sms.StudentManagmentSystem.enrollment.EnrollmentRepository;
import com.sms.StudentManagmentSystem.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService{

    private final GradeRepository gradeRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final GradeMapper gradeMapper;

    @Override
    public GradeResponse createGrade(GradeCreateRequest gradeCreateRequest) {

        Grade grade = gradeMapper.toEntity(gradeCreateRequest);
        Enrollment enrollment = enrollmentRepository.findById(gradeCreateRequest.getEnrollmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Enrollment not found with ID: " + gradeCreateRequest.getEnrollmentId()));

        grade.setEnrollment(enrollment);

        return gradeMapper.toResponse(gradeRepository.save(grade));
    }

    @Override
    public GradeResponse getGradeByStudentId(Long studentId) {

       Grade grade= gradeRepository.findByEnrollmentStudentStudentId(studentId)
                .orElseThrow(()->new ResourceNotFoundException("Grade not found with enrollment Id "+studentId));
        return gradeMapper.toResponse(grade);
    }

    @Override
    public GradeResponse getGradeByEnrollmentId(Long enrollmentId) {

        Grade grade=gradeRepository.findByEnrollmentEnrollmentId(enrollmentId)
                .orElseThrow(()->new ResourceNotFoundException("Grade not found with enrollment Id "+enrollmentId));
        return gradeMapper.toResponse(grade);
    }

    @Override
    public GradeResponse updateGrade(GradeUpdateRequest gradeUpdateRequest, Long id) {
        Grade gradeFromDb=gradeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Grade not found with Id "+id));
        Enrollment enrollment = enrollmentRepository.findById(gradeUpdateRequest.getEnrollmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Enrollment not found with ID: " + gradeUpdateRequest.getEnrollmentId()));
        gradeFromDb.setDescription(gradeUpdateRequest.getDescription());
        gradeFromDb.setTitle(gradeUpdateRequest.getTitle());
        gradeFromDb.setScore(gradeUpdateRequest.getScore());
        gradeFromDb.setMaxScore(gradeUpdateRequest.getMaxScore());
        gradeFromDb.setGradeDate(gradeUpdateRequest.getGradeDate());
        gradeFromDb.setGradeType(gradeUpdateRequest.getGradeType());
        gradeFromDb.setEnrollment(enrollment);
        return gradeMapper.toResponse(gradeRepository.save(gradeFromDb));
    }
}
