package com.sms.StudentManagmentSystem.grade;


import com.sms.StudentManagmentSystem.enrollment.Enrollment;
import com.sms.StudentManagmentSystem.enrollment.EnrollmentRepository;
import com.sms.StudentManagmentSystem.enrollment.EnrollmentStatus;
import com.sms.StudentManagmentSystem.exception.BusinessException;
import com.sms.StudentManagmentSystem.exception.ResourceNotFoundException;
import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;
import com.sms.StudentManagmentSystem.student.Student;
import com.sms.StudentManagmentSystem.student.StudentAccessService;
import com.sms.StudentManagmentSystem.student.StudentRepository;
import com.sms.StudentManagmentSystem.teacher.TeacherAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final GradeMapper gradeMapper;
    private final TeacherAccessService teacherAccessService;
    private final StudentAccessService studentAccessService;

    @Override
    public GradeResponse createGrade(GradeCreateRequest gradeCreateRequest) {

        Grade grade = gradeMapper.toEntity(gradeCreateRequest);
        Enrollment enrollment = enrollmentRepository.findById(gradeCreateRequest.getEnrollmentId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Enrollment not found with ID: " + gradeCreateRequest.getEnrollmentId()));

        teacherAccessService.checkTeacherCanAccessEnrollment(enrollment);
        validateScore(grade.getGradeType(), grade.getScore());


        if (
                gradeRepository.existsByEnrollmentEnrollmentIdAndGradeType(
                        gradeCreateRequest.getEnrollmentId(),
                        grade.getGradeType())
        ) {
            throw new BusinessException("Grade already exists for this type");
        }

        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new BusinessException("Grade can only be added to active enrollment");
        }
        grade.setEnrollment(enrollment);
        grade.setMaxScore(grade.getGradeType().getMaxScore());
        return gradeMapper.toResponse(gradeRepository.save(grade));
    }


    @Override
    public List<GradeResponse> getGradeByStudentId(Long studentId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));

        List<Grade> grades = gradeRepository.findByEnrollmentStudentStudentId(studentId);
        grades.forEach(grade -> teacherAccessService.checkTeacherCanAccessEnrollment(grade.getEnrollment()));

        return grades.stream().map(gradeMapper::toResponse).toList();
    }

    @Override
    public List<GradeResponse> getGradeByEnrollmentId(Long enrollmentId) {
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with ID: " + enrollmentId));

        teacherAccessService.checkTeacherCanAccessEnrollment(enrollment);
        List<Grade> grades = gradeRepository.findByEnrollmentEnrollmentId(enrollmentId);
        return grades.stream().map(gradeMapper::toResponse).toList();
    }

    @Override
    public GradeResponse updateGrade(GradeUpdateRequest gradeUpdateRequest, Long id) {
        Grade gradeFromDb = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with Id " + id));

        Enrollment enrollment = gradeFromDb.getEnrollment();

        teacherAccessService.checkTeacherCanAccessEnrollment(enrollment);
        validateScore(gradeFromDb.getGradeType(), gradeUpdateRequest.getScore());

        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new BusinessException("Grade can only be updated for active enrollment");
        }

        gradeFromDb.setDescription(gradeUpdateRequest.getDescription());
        gradeFromDb.setTitle(gradeUpdateRequest.getTitle());
        gradeFromDb.setScore(gradeUpdateRequest.getScore());
        gradeFromDb.setGradeDate(gradeUpdateRequest.getGradeDate());
        return gradeMapper.toResponse(gradeRepository.save(gradeFromDb));
    }

    @Override
    public GradeMaxScoreResponse getGradeByEnrollmentIdSummary(Long enrollmentId) {

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with ID: " + enrollmentId));


        teacherAccessService.checkTeacherCanAccessEnrollment(enrollment);

        List<Grade> grades = gradeRepository.findByEnrollmentEnrollmentId(enrollmentId);
        int totalMaxScore = grades.stream().mapToInt(grade -> grade.getMaxScore()).sum();

        int totalScore = grades.stream().mapToInt(Grade::getScore).sum();

        GradeMaxScoreResponse gradeMaxScoreResponse = new GradeMaxScoreResponse();

        gradeMaxScoreResponse.setEnrollmentId(enrollmentId);
        gradeMaxScoreResponse.setTotalScore(totalScore);
        gradeMaxScoreResponse.setTotalMaxScore(totalMaxScore);
        gradeMaxScoreResponse.setPercentage(((double) totalScore / totalMaxScore) * 100);

        List<GradeDetailResponse> responseList = grades.stream().map(grade -> {
            GradeDetailResponse gradeDetailResponse = new GradeDetailResponse();

            gradeDetailResponse.setGradeType(grade.getGradeType());
            gradeDetailResponse.setScore(grade.getScore());
            gradeDetailResponse.setMaxScore(grade.getMaxScore());
            return gradeDetailResponse;
        }).toList();
        gradeMaxScoreResponse.setGrades(responseList);
        return gradeMaxScoreResponse;
    }

    @Override
    public ApiMessageResponse deleteGrade(Long id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Grade not found with ID " + id));

        teacherAccessService.checkTeacherCanAccessEnrollment(grade.getEnrollment());
        gradeRepository.delete(grade);
        return new ApiMessageResponse("Grade successfully deleted with ID " + id);
    }

    @Override
    public List<GradeResponse> getMyGrades() {

        Student currentStudent = studentAccessService.getCurrentStudent();
        List<Grade> myGrades = gradeRepository.findByEnrollmentStudentStudentId(currentStudent.getStudentId());
        return myGrades.stream().map(gradeMapper::toResponse).toList();
    }

    private void validateScore(GradeType gradeType, Integer score) {

        if (score > gradeType.getMaxScore()) {
            throw new BusinessException(
                    "Score cannot be greater than max score for " + gradeType +
                            ". Max score is " + gradeType.getMaxScore()
            );
        }
    }
}
