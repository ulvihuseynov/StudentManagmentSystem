package com.sms.StudentManagmentSystem.course;

import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;

import java.util.List;

public interface CourseService {
    CourseResponse createCourse( CourseCreateRequest courseCreateRequest);

    List<CourseResponse> getAllCourse();

    CourseResponse getCourseById(Long id);

    CourseResponse updateCourse( CourseUpdateRequest courseUpdateRequest, Long id);

    CourseResponse updateCourseStatus( CourseUpdateStatus courseUpdateStatus, Long id);

    ApiMessageResponse deactivateCourse(Long id);
}
