package com.sms.StudentManagmentSystem.group;

import jakarta.validation.Valid;

import java.util.List;

public interface GroupService {
    GroupResponse createGroup( GroupCreateRequest groupCreateRequest, Long courseId, Long teacherId);

    List<GroupResponse> getAllGroup();

    GroupResponse getGroupById(Long id);

    GroupResponse updateGroup( GroupUpdateRequest groupUpdateRequest, Long id, Long courseId, Long teacherId);

    GroupResponse updateGroupStatus( GroupUpdateStatus groupUpdateStatus, Long id);
}
