package com.sms.StudentManagmentSystem.group;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupMapper {


//    @Mapping(source = "course",target = "course")
//    @Mapping(source = "teacher",target = "teacher")
    Group toEntity (GroupCreateRequest groupCreateRequest);

    @Mapping(source = "course",target = "course")
    @Mapping(source = "teacher",target = "teacher")
    GroupResponse toResponse(Group group);
}
