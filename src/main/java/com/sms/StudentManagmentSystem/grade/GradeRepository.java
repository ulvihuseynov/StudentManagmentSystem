package com.sms.StudentManagmentSystem.grade;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GradeRepository extends JpaRepository<Grade ,Long> {
    Optional<Grade> findByEnrollmentEnrollmentId(Long enrollmentId);

    Optional<Grade> findByEnrollmentStudentStudentId(Long studentId);
}
