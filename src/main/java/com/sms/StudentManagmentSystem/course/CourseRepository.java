package com.sms.StudentManagmentSystem.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    boolean existsByName(String name);

    boolean existsByNameAndCourseIdNot( String name, Long id);
}
