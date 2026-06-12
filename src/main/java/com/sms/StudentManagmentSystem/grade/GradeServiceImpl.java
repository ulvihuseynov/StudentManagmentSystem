package com.sms.StudentManagmentSystem.grade;


import com.sms.StudentManagmentSystem.enrollment.Enrollment;
import com.sms.StudentManagmentSystem.enrollment.EnrollmentRepository;
import com.sms.StudentManagmentSystem.enrollment.EnrollmentStatus;
import com.sms.StudentManagmentSystem.exception.BusinessException;
import com.sms.StudentManagmentSystem.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

        if (grade.getScore()>grade.getMaxScore()){
            throw new BusinessException("Score cannot be greater than  max score");
        }

        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE){
            throw new BusinessException("Grade can only be added to active enrollment");
        }
        grade.setEnrollment(enrollment);

        return gradeMapper.toResponse(gradeRepository.save(grade));
    }

    @Override
    public  List<GradeResponse> getGradeByStudentId(Long studentId) {

        List<Grade> grades = gradeRepository.findByEnrollmentStudentStudentId(studentId);

        return grades.stream().map(gradeMapper::toResponse).toList();
    }

    @Override
    public  List<GradeResponse> getGradeByEnrollmentId(Long enrollmentId) {

       List<Grade> grades=gradeRepository.findByEnrollmentEnrollmentId(enrollmentId);
        return grades.stream().map(gradeMapper::toResponse).toList();
    }

    @Override
    public GradeResponse updateGrade(GradeUpdateRequest gradeUpdateRequest, Long id) {
        Grade gradeFromDb=gradeRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Grade not found with Id "+id));
        Enrollment enrollment = enrollmentRepository.findById(gradeUpdateRequest.getEnrollmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Enrollment not found with ID: " + gradeUpdateRequest.getEnrollmentId()));


        if (gradeUpdateRequest.getScore()>gradeUpdateRequest.getMaxScore()){
            throw new BusinessException("Score is not bigger from max score");
        }
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
