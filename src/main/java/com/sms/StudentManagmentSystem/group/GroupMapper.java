package com.sms.StudentManagmentSystem.group;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GroupMapper {



    Group toEntity (GroupCreateRequest groupCreateRequest);


    GroupResponse toResponse(Group group);
}
