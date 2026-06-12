package com.sms.StudentManagmentSystem.grade;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GradeMapper {

    Grade toEntity (GradeCreateRequest gradeCreateRequest);

    GradeResponse toResponse (Grade grade);
}
