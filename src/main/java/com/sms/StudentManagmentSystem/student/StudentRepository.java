package com.sms.StudentManagmentSystem.student;

import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);
    Boolean existsByEmail(String email);

    boolean existsByEmailAndStudentIdNot( String email, Long id);
}
