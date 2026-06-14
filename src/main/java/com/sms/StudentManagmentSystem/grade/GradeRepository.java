package com.sms.StudentManagmentSystem.grade;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade ,Long> {
    List<Grade> findByEnrollmentEnrollmentId(Long enrollmentId);

    List<Grade> findByEnrollmentStudentStudentId(Long studentId);

    boolean existsByEnrollmentEnrollmentIdAndGradeType(Long enrollmentId, GradeType gradeType);

}
