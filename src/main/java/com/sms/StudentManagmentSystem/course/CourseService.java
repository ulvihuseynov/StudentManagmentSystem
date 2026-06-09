package com.sms.StudentManagmentSystem.course;

import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface CourseService {
    CourseResponse createCourse( CourseCreateRequest courseCreateRequest);

    List<CourseResponse> getAllCourse();

    CourseResponse getCourseById(Long id);

    CourseResponse updateCourse(@Valid CourseUpdateRequest courseUpdateRequest, Long id);

    CourseResponse updateCourseStatus(@Valid CourseUpdateStatus courseUpdateStatus, Long id);

    ApiMessageResponse deactivateCourse(Long id);
}
