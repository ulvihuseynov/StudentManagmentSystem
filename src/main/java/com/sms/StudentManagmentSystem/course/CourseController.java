package com.sms.StudentManagmentSystem.course;

import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@Valid @RequestBody CourseCreateRequest courseCreateRequest){

        CourseResponse courseResponse=courseService.createCourse(courseCreateRequest);

        return new ResponseEntity<>(courseResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourse(){

        List<CourseResponse> courseResponses= courseService.getAllCourse();

        return new ResponseEntity<>(courseResponses, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> getCourseById(@PathVariable Long id){

        CourseResponse courseResponse= courseService.getCourseById(id);

        return new ResponseEntity<>(courseResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponse> updateCourse(@Valid @RequestBody CourseUpdateRequest courseUpdateRequest,
                                                         @PathVariable Long id){

        CourseResponse updateCourse= courseService.updateCourse(courseUpdateRequest,id);

        return new ResponseEntity<>(updateCourse, HttpStatus.OK);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CourseResponse> updateCourseStatus(@Valid @RequestBody CourseUpdateStatus courseUpdateStatus,
                                                               @PathVariable Long id){

        CourseResponse updateCourseStatus= courseService.updateCourseStatus(courseUpdateStatus,id);

        return new ResponseEntity<>(updateCourseStatus, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiMessageResponse> deleteCourse(@PathVariable Long id){

        ApiMessageResponse status= courseService.deactivateCourse(id);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

}
