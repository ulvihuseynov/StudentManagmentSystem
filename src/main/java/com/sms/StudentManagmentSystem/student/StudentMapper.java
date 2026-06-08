package com.sms.StudentManagmentSystem.student;

import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface StudentMapper {

    Student toEntity(StudentCreateRequest studentCreateRequest);

    StudentResponse toResponse(Student student);
}
