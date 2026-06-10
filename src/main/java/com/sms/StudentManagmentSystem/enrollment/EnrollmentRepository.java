package com.sms.StudentManagmentSystem.enrollment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
    List<Enrollment> findByStudent_StudentId(Long studentId);


    List<Enrollment> findByGroup_GroupId(Long groupId);



    boolean existsByStudent_StudentId(Long studentId);

    boolean existsByGroup_GroupId(Long groupId);

    Long countByStudent_StudentId(Long studentId);
}
