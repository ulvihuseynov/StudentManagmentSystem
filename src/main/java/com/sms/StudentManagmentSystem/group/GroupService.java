package com.sms.StudentManagmentSystem.group;

import jakarta.validation.Valid;

import java.util.List;

public interface GroupService {
    GroupResponse createGroup( GroupCreateRequest groupCreateRequest);

    List<GroupResponse> getAllGroup();

    GroupResponse getGroupById(Long id);

    GroupResponse updateGroup( GroupUpdateRequest groupUpdateRequest, Long id);

    GroupResponse updateGroupStatus( GroupUpdateStatus groupUpdateStatus, Long id);
}
