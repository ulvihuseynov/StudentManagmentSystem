package com.sms.StudentManagmentSystem.grade;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;


    @PostMapping
    public ResponseEntity<GradeResponse> createGrade(@Valid @RequestBody GradeCreateRequest gradeCreateRequest){

        GradeResponse gradeResponse= gradeService.createGrade(gradeCreateRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(gradeResponse);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GradeResponse>> getGradeByStudentId(@PathVariable Long studentId){

        List<GradeResponse> gradeResponse= gradeService.getGradeByStudentId(studentId);

        return ResponseEntity.status(HttpStatus.OK).body(gradeResponse);
    }

    @GetMapping("/enrollment/{enrollmentId}")
    public ResponseEntity<List<GradeResponse>> getGradeByEnrollmentId(@PathVariable Long enrollmentId){

      List<GradeResponse> gradeResponse= gradeService.getGradeByEnrollmentId(enrollmentId);

        return ResponseEntity.status(HttpStatus.OK).body(gradeResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<GradeResponse> updateGrade(@Valid @RequestBody GradeUpdateRequest gradeUpdateRequest,
                                                     @PathVariable Long id){

        GradeResponse gradeResponse= gradeService.updateGrade(gradeUpdateRequest,id);

        return ResponseEntity.status(HttpStatus.OK).body(gradeResponse);
    }
}
