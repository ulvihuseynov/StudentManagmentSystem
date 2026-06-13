package com.sms.StudentManagmentSystem.teacher;

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
public class TeacherAccessService {

    private final TeacherRepository teacherRepository;

    public void checkTeacherCanAccessEnrollment(Enrollment enrollment) {

        Teacher currentTeacher = getCurrentTeacher();
        Long currentTeacherId = currentTeacher.getTeacherId();
        Long teacherId = enrollment.getGroup().getTeacher().getTeacherId();
        if (!currentTeacherId.equals(teacherId)) {
            throw new ForbiddenException("You are not allowed to access this enrollment");
        }
    }

    private Teacher getCurrentTeacher() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (authentication == null || username == null) {
            throw new ForbiddenException("Authentication is required");
        }
        return teacherRepository.findByUserUsername(username).
                orElseThrow(() -> new ResourceNotFoundException("Teacher profile not found for current user"));
    }

    private boolean isAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication==null){
            return false;
        }
        return authentication.getAuthorities().stream().anyMatch(
                grantedAuthority -> Objects.equals(grantedAuthority.getAuthority(), "ROLE_ADMIN")
        );
    }
}
