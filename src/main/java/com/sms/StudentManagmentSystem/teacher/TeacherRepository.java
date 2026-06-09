package com.sms.StudentManagmentSystem.teacher;

import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    boolean existsByEmail( String email);

    boolean existsByEmailAndTeacherIdNot( String email, Long id);
}
