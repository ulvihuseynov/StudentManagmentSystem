package com.sms.StudentManagmentSystem.attendance;

import com.sms.StudentManagmentSystem.enrollment.Enrollment;
import com.sms.StudentManagmentSystem.enrollment.EnrollmentRepository;
import com.sms.StudentManagmentSystem.enrollment.EnrollmentStatus;
import com.sms.StudentManagmentSystem.exception.BusinessException;
import com.sms.StudentManagmentSystem.exception.ResourceNotFoundException;
import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService{

    private final AttendanceRepository attendanceRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AttendanceMapper attendanceMapper;

    @Override
    public AttendanceResponse createAttendance(AttendanceCreateRequest attendanceCreateRequest) {

        Attendance attendance = attendanceMapper.toEntity(attendanceCreateRequest);

        Enrollment enrollment = enrollmentRepository.findById(attendanceCreateRequest.getEnrollmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with ID " + attendanceCreateRequest.getEnrollmentId()));

        if (enrollment.getStatus() != EnrollmentStatus.ACTIVE) {
            throw new BusinessException("Attendance can only be added to active enrollment");
        }

        if (attendanceRepository.existsByEnrollmentEnrollmentIdAndLessonDate(attendanceCreateRequest.getEnrollmentId(),
                attendanceCreateRequest.getLessonDate())){
            throw new BusinessException("Attendance already exists for this lesson date");

        }

        attendance.setEnrollment(enrollment);

        return attendanceMapper.toResponse(attendanceRepository.save(attendance));
    }

    @Override
    public List<AttendanceResponse> attendanceByStudentId(Long studentId) {

        List<Attendance> attendances=attendanceRepository.findByEnrollmentStudentStudentId(studentId);
        return attendances.stream().map(attendanceMapper::toResponse)
                .toList();
    }

    @Override
    public List<AttendanceResponse> attendanceByEnrollmentId(Long enrollmentId) {
        List<Attendance> attendances=attendanceRepository.findByEnrollmentEnrollmentId(enrollmentId);
        return attendances.stream().map(attendanceMapper::toResponse)
                .toList();
    }

    @Override
    public AttendanceResponse updateAttendance(AttendanceUpdateRequest attendanceUpdateRequest, Long attendanceId) {


        Attendance attendanceFromDb = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with ID " + attendanceId));

        Enrollment enrollment = attendanceFromDb.getEnrollment();

        if (attendanceRepository.existsByEnrollmentEnrollmentIdAndLessonDateAndAttendanceIdNot(
                enrollment.getEnrollmentId(),
                attendanceUpdateRequest.getLessonDate(),
                attendanceId
        )) {
            throw new BusinessException("Attendance already exists for this lesson date");
        }
        attendanceFromDb.setNote(attendanceUpdateRequest.getNote());
        attendanceFromDb.setStatus(attendanceUpdateRequest.getStatus());
        attendanceFromDb.setLessonDate(attendanceUpdateRequest.getLessonDate());


        return attendanceMapper.toResponse(attendanceRepository.save(attendanceFromDb));
    }

    @Override
    public ApiMessageResponse deleteAttendance(Long attendanceId) {

        Attendance attendance= attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance not found with ID " + attendanceId));

        attendanceRepository.delete(attendance);
        return new ApiMessageResponse("Attendance deleted successfully with ID "+attendanceId);
    }
}
