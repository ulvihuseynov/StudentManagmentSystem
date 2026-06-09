package com.sms.StudentManagmentSystem.teacher;

import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface TeacherService {
    TeacherResponse createTeacher( TeacherCreateRequest teacherCreateRequest);

    List<TeacherResponse> getAllTeacher();

    TeacherResponse getTeacherById(Long id);

    TeacherResponse updateTeacher(@Valid TeacherUpdateRequest teacherUpdateRequest, Long id);

    TeacherResponse updateStatus(@Valid TeacherStatusRequest teacherStatusRequest, Long id);

    ApiMessageResponse deactivateTeacher(Long id);
}
