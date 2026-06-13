package com.sms.StudentManagmentSystem.attendance;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {


    List<Attendance> findByEnrollmentEnrollmentId(Long enrollmentId);

    List<Attendance> findByEnrollmentStudentStudentId(Long studentId);


    boolean existsByEnrollmentEnrollmentIdAndLessonDate(@NotNull(message = "EnrollmentId is required") Long enrollmentId, @NotNull(message = "Lesson date is required") LocalDate lessonDate);

    boolean existsByEnrollmentEnrollmentIdAndLessonDateAndAttendanceIdNot(Long enrollmentId, @NotNull(message = "Lesson date is required") LocalDate lessonDate, Long attendanceId);
}
