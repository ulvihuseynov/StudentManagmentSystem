package com.sms.StudentManagmentSystem.teacher;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TeacherMapper {

    Teacher toEntity(TeacherCreateRequest teacherCreateRequest);

    TeacherResponse toResponse(Teacher teacher);

}
