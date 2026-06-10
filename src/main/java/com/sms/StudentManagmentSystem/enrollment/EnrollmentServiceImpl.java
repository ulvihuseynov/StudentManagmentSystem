package com.sms.StudentManagmentSystem.enrollment;

import com.sms.StudentManagmentSystem.exception.BusinessException;
import com.sms.StudentManagmentSystem.exception.DuplicateResourceException;
import com.sms.StudentManagmentSystem.exception.ResourceNotFoundException;
import com.sms.StudentManagmentSystem.group.Group;
import com.sms.StudentManagmentSystem.group.GroupRepository;
import com.sms.StudentManagmentSystem.group.GroupStatus;
import com.sms.StudentManagmentSystem.student.Student;
import com.sms.StudentManagmentSystem.student.StudentRepository;
import com.sms.StudentManagmentSystem.student.StudentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Override
    public EnrollmentResponse createEnrollment(EnrollmentCreateRequest enrollmentCreateRequest) {

        Enrollment enrollment = enrollmentMapper.toEntity(enrollmentCreateRequest);

        Group group = groupRepository.findById(enrollmentCreateRequest.getGroupId()).orElseThrow(() -> new ResourceNotFoundException("Group not found with ID: " + enrollmentCreateRequest.getGroupId()));

        Student student = studentRepository.findById(enrollmentCreateRequest.getStudentId()).orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + enrollmentCreateRequest.getStudentId()));

        Long studentCount = enrollmentRepository.countByGroup_GroupIdAndStatus(group.getGroupId(), EnrollmentStatus.ACTIVE);

        validateGroupCapacity(group.getCapacity(), studentCount);
        validateGroupStatus(group);
        validateStudentStatus(student);
        validateIsStudent(student.getStudentId(), group.getGroupId());


        enrollment.setStudent(student);
        enrollment.setGroup(group);
        enrollment.setStatus(EnrollmentStatus.ACTIVE);

        return enrollmentMapper.toResponse(enrollmentRepository.save(enrollment));
    }


    @Override
    public List<EnrollmentResponse> getAllEnrollment() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        return enrollments.stream().map(enrollmentMapper::toResponse).toList();
    }

    @Override
    public List<EnrollmentResponse> getEnrollmentByStudent(Long studentId) {

        boolean isStudent = enrollmentRepository.existsByStudent_StudentId(studentId);

        if (!isStudent) {
            throw new ResourceNotFoundException("Student is not found in the group with ID " + studentId);
        }
        List<Enrollment> enrollments = enrollmentRepository.findByStudent_StudentId(studentId);

        return enrollments.stream().map(enrollmentMapper::toResponse).toList();
    }

    @Override
    public List<EnrollmentResponse> getEnrollmentByGroup(Long groupId) {

        boolean isGroup = enrollmentRepository.existsByGroup_GroupId(groupId);

        if (!isGroup) {
            throw new ResourceNotFoundException("Group is not found in the group with ID " + groupId);
        }
        List<Enrollment> enrollments = enrollmentRepository.findByGroup_GroupId(groupId);

        return enrollments.stream().map(enrollmentMapper::toResponse).toList();
    }

    @Override
    public EnrollmentResponse getEnrollmentStatusUpdate(EnrollmentUpdateStatus enrollmentUpdateStatus, Long id) {
        Enrollment enrollment = getEnrollment(id);
        enrollment.setStatus(enrollmentUpdateStatus.getStatus());
        enrollmentRepository.save(enrollment);
        return enrollmentMapper.toResponse(enrollment);
    }

    @Override
    public EnrollmentResponse deactiveEnrollment(Long id) {
        Enrollment enrollment = getEnrollment(id);
        enrollment.setStatus(EnrollmentStatus.CANCELLED);
        enrollmentRepository.save(enrollment);
        return enrollmentMapper.toResponse(enrollment);
    }

    private Enrollment getEnrollment(Long id) {

        return enrollmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Enrollment not found with ID: " + id));
    }

    private void validateIsStudent(Long studentId, Long groupId) {

        boolean isStudent = enrollmentRepository.existsByStudent_StudentIdAndGroup_GroupId(studentId, groupId);

        if (isStudent) {
            throw new DuplicateResourceException("Student is already exists in the group");
        }
    }

    private void validateGroupCapacity(Integer capacity, long activeStudentCount) {


        if (activeStudentCount >=  capacity) {
            throw new BusinessException("Group capacity is full");
        }
    }

    private void validateStudentStatus(Student student) {

        if (student.getStatus() != StudentStatus.ACTIVE) {
            throw new BusinessException("Student is not Active");
        }
    }

    private void validateGroupStatus(Group group) {

        if (group.getStatus() != GroupStatus.ACTIVE) {
            throw new BusinessException("Group is not Active");
        }
    }
}