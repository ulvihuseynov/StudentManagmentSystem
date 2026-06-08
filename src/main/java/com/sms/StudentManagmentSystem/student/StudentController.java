package com.sms.StudentManagmentSystem.student;

import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentCreateRequest studentCreateRequest){

        StudentResponse studentResponse= studentService.createStudent(studentCreateRequest);

        return new ResponseEntity<>(studentResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudent(){

        List<StudentResponse> studentResponse= studentService.getAllStudent();

        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(@PathVariable Long id){

        StudentResponse studentResponse= studentService.getStudentById(id);

        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(@Valid @RequestBody StudentUpdateRequest studentUpdateRequest,
                                                @PathVariable Long id){

        StudentResponse updateStudent= studentService.updateStudent(studentUpdateRequest,id);

        return new ResponseEntity<>(updateStudent, HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<StudentStatus> updateStudentStatus(@Valid @RequestBody StudentStatusRequest studentStatusRequest,
                                                         @PathVariable Long id){

        StudentStatus updateStudentStatus= studentService.updateStudentStatus(studentStatusRequest,id);

        return new ResponseEntity<>(updateStudentStatus, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiMessageResponse> deleteStudent(@PathVariable Long id){

        ApiMessageResponse status= studentService.deactivateStudent(id);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }
}
