package com.sms.StudentManagmentSystem.student;

import com.sms.StudentManagmentSystem.enrollment.Enrollment;
import com.sms.StudentManagmentSystem.exception.ForbiddenException;
import com.sms.StudentManagmentSystem.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class StudentAccessService {

    private final StudentRepository studentRepository;

    public void checkStudentCanAccessEnrollment(Enrollment enrollment) {

        Student currentStudent = getCurrentTeacher();
        Long currentStudentId = currentStudent.getStudentId();
        Long studentId = enrollment.getStudent().getStudentId();
        if (!currentStudentId.equals(studentId)) {
            throw new ForbiddenException("You are not allowed to access this enrollment");
        }
    }

    private Student getCurrentTeacher() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (authentication == null || username == null) {
            throw new ForbiddenException("Authentication is required");
        }
        return studentRepository.findByUserUsername(username).
                orElseThrow(() -> new ResourceNotFoundException("Student profile not found for current user"));
    }

    private boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream().anyMatch(
                grantedAuthority -> Objects.equals(grantedAuthority.getAuthority(), "ROLE_ADMIN")
        );
    }
}


