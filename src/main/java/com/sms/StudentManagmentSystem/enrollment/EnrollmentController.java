package com.sms.StudentManagmentSystem.enrollment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<EnrollmentResponse> createEnrollment(@Valid @RequestBody EnrollmentCreateRequest enrollmentCreateRequest){

        EnrollmentResponse enrollmentResponse=enrollmentService.createEnrollment(enrollmentCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(enrollmentResponse);
    }

    @GetMapping
    public ResponseEntity<List<EnrollmentResponse>> getAllEnrollment(){

        List<EnrollmentResponse> enrollmentResponse=enrollmentService.getAllEnrollment();

        return ResponseEntity.status(HttpStatus.OK).body(enrollmentResponse);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentResponse>> getEnrollmentByStudent(@PathVariable Long studentId){

        List<EnrollmentResponse> enrollmentResponse=enrollmentService.getEnrollmentByStudent(studentId);

        return ResponseEntity.status(HttpStatus.OK).body(enrollmentResponse);
    }


    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<EnrollmentResponse>> getEnrollmentByGroup(@PathVariable Long groupId){

        List<EnrollmentResponse> enrollmentResponse=enrollmentService.getEnrollmentByGroup(groupId);

        return ResponseEntity.status(HttpStatus.OK).body(enrollmentResponse);
    }


    @PatchMapping("/{id}/status")
    public ResponseEntity<EnrollmentResponse> updateEnrollmentStatus(@Valid @RequestBody EnrollmentUpdateStatus enrollmentUpdateStatus,
                                                                        @PathVariable Long id){

       EnrollmentResponse enrollmentResponse=enrollmentService.updateEnrollmentStatus(enrollmentUpdateStatus,id);

        return ResponseEntity.status(HttpStatus.OK).body(enrollmentResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EnrollmentResponse> deleteEnrollment(@PathVariable Long id){

        EnrollmentResponse enrollmentResponse=enrollmentService.deactiveEnrollment(id);

        return ResponseEntity.status(HttpStatus.OK).body(enrollmentResponse);
    }
}
