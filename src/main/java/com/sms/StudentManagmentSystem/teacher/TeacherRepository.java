package com.sms.StudentManagmentSystem.teacher;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    boolean existsByEmail( String email);

    boolean existsByEmailAndTeacherIdNot( String email, Long id);

    Optional<Teacher> findByFirstname(String firstname);
}
