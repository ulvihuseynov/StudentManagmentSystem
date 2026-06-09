package com.sms.StudentManagmentSystem.course;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CourseMapper {

    Course toEntity (CourseCreateRequest courseCreateRequest);

    CourseResponse toResponse (Course course);
}
