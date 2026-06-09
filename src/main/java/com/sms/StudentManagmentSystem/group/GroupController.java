package com.sms.StudentManagmentSystem.group;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody GroupCreateRequest groupCreateRequest){
        GroupResponse groupResponse= groupService.createGroup(groupCreateRequest);

        return new ResponseEntity<>(groupResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAllGroup(){

        List<GroupResponse> groupResponse= groupService.getAllGroup();

        return new ResponseEntity<>(groupResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> getGroupById(@PathVariable Long id){

        GroupResponse groupResponse= groupService.getGroupById(id);

        return new ResponseEntity<>(groupResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponse> updateGroup(@Valid @RequestBody GroupUpdateRequest groupUpdateRequest,
                                                     @PathVariable Long id){

        GroupResponse groupResponse= groupService.updateGroup(groupUpdateRequest,id);

        return new ResponseEntity<>(groupResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<GroupResponse> updateGroupStatus(@Valid @RequestBody GroupUpdateStatus groupUpdateStatus,
                                                         @PathVariable Long id){

        GroupResponse groupResponse= groupService.updateGroupStatus(groupUpdateStatus,id);

        return new ResponseEntity<>(groupResponse, HttpStatus.OK);
    }
}
