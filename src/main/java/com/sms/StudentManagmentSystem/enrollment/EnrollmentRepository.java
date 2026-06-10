package com.sms.StudentManagmentSystem.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    List<Enrollment> findByStudent_StudentId(Long studentId);


    List<Enrollment> findByGroup_GroupId(Long groupId);


    boolean existsByStudent_StudentIdAndGroup_GroupId(Long studentId, Long groupId);


    long countByGroup_GroupIdAndStatus(Long groupId, EnrollmentStatus enrollmentStatus);
}
