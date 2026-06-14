package com.sms.StudentManagmentSystem.attendance;

import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<AttendanceResponse> createAttendance(@Valid @RequestBody AttendanceCreateRequest attendanceCreateRequest){

        AttendanceResponse attendanceResponse=attendanceService.createAttendance(attendanceCreateRequest);

        return new ResponseEntity<>(attendanceResponse, HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<AttendanceResponse>> attendanceByStudentId(@PathVariable Long studentId){

        List<AttendanceResponse> attendanceResponse=attendanceService.attendanceByStudentId(studentId);

        return new ResponseEntity<>(attendanceResponse, HttpStatus.OK);
    }

    @GetMapping("/enrollment/{enrollmentId}")
    public ResponseEntity<List<AttendanceResponse>> attendanceByEnrollmentId(@PathVariable Long enrollmentId){

        List<AttendanceResponse> attendanceResponse=attendanceService.attendanceByEnrollmentId(enrollmentId);

        return new ResponseEntity<>(attendanceResponse, HttpStatus.OK);
    }

    @PutMapping("/{attendanceId}")
    public ResponseEntity<AttendanceResponse> updateAttendance(@Valid @RequestBody AttendanceUpdateRequest attendanceUpdateRequest,
                                                               @PathVariable Long attendanceId){

        AttendanceResponse attendanceResponse=attendanceService.updateAttendance(attendanceUpdateRequest,attendanceId);

        return new ResponseEntity<>(attendanceResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{attendanceId}")
    public ResponseEntity<ApiMessageResponse> deleteAttendance(@PathVariable Long attendanceId){

        ApiMessageResponse attendanceResponse=attendanceService.deleteAttendance(attendanceId);

        return new ResponseEntity<>(attendanceResponse, HttpStatus.OK);
    }


    @GetMapping("/me")
    public ResponseEntity<List<AttendanceResponse>> getMyAttendances() {

        List<AttendanceResponse> attendanceResponses = attendanceService.getMyAttendances();

        return ResponseEntity.status(HttpStatus.OK).body(attendanceResponses);
    }
}
