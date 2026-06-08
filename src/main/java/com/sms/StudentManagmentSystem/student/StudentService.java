package com.sms.StudentManagmentSystem.student;

import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface StudentService {
    StudentResponse createStudent( StudentCreateRequest studentCreateRequest);

    List<StudentResponse> getAllStudent();

    StudentResponse getStudentById(Long id);


    StudentResponse updateStudent(StudentUpdateRequest studentUpdateRequest, Long id);

    StudentStatus updateStudentStatus(StudentStatusRequest studentStatusRequest, Long id);

    ApiMessageResponse deactivateStudent(Long id);
}
