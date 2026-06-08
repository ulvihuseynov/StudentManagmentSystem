package com.sms.StudentManagmentSystem.student;

import jakarta.validation.Valid;

import java.util.List;

public interface StudentService {
    StudentResponse createStudent( StudentCreateRequest studentCreateRequest);

    List<StudentResponse> getAllStudent();

    StudentResponse getStudentById(Long id);

    String deleteStudent(Long id);

    StudentResponse updateStudent(StudentCreateRequest studentCreateRequest, Long id);

    StudentStatus updateStudentStatus(StudentStatusRequest studentStatusRequest, Long id);
}
