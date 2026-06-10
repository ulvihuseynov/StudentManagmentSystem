package com.sms.StudentManagmentSystem.enrollment;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EnrollmentMapper {

    Enrollment toEntity(EnrollmentCreateRequest enrollmentCreateRequest);

    EnrollmentResponse toResponse(Enrollment enrollment);
}
