package com.sms.StudentManagmentSystem.attendance;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttendanceMapper {

    Attendance toEntity (AttendanceCreateRequest attendanceCreateRequest);

    AttendanceResponse toResponse(Attendance attendance);
}
