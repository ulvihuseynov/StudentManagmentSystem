package com.sms.StudentManagmentSystem.teacher;

import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;
import com.sms.StudentManagmentSystem.student.Student;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherResponse> createTeacher(@Valid @RequestBody TeacherCreateRequest teacherCreateRequest) {

        TeacherResponse teacherResponse = teacherService.createTeacher(teacherCreateRequest);
        return new ResponseEntity<>(teacherResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TeacherResponse>> getAllTeacher() {

        List<TeacherResponse> teacherResponse = teacherService.getAllTeacher();
        return new ResponseEntity<>(teacherResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponse> getTeacherById(@PathVariable Long id) {

        TeacherResponse teacherResponse = teacherService.getTeacherById(id);
        return new ResponseEntity<>(teacherResponse, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponse> updateTeacher(@Valid @RequestBody TeacherUpdateRequest teacherUpdateRequest,
                                                         @PathVariable Long id) {

        TeacherResponse teacherResponse = teacherService.updateTeacher(teacherUpdateRequest, id);
        return new ResponseEntity<>(teacherResponse, HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TeacherResponse> updateStatus(@Valid @RequestBody TeacherStatusRequest teacherStatusRequest,
                                                        @PathVariable Long id) {

        TeacherResponse teacherResponse = teacherService.updateStatus(teacherStatusRequest, id);
        return new ResponseEntity<>(teacherResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiMessageResponse> deleteTeacher(@PathVariable Long id) {

        ApiMessageResponse messageResponse = teacherService.deactivateTeacher(id);
        return new ResponseEntity<>(messageResponse, HttpStatus.OK);
    }


    @GetMapping("/me")
    public ResponseEntity<TeacherResponse> getTeacherCurrentProfile(){

        TeacherResponse teacherResponse= teacherService.getTeacherCurrentProfile();

        return new ResponseEntity<>(teacherResponse, HttpStatus.OK);
    }
}
