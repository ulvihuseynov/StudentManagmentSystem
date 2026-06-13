package com.sms.StudentManagmentSystem.student;

import com.sms.StudentManagmentSystem.auth.*;
import com.sms.StudentManagmentSystem.exception.DuplicateResourceException;
import com.sms.StudentManagmentSystem.exception.ResourceNotFoundException;
import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final StudentMapper studentMapper;

    @Override
    public StudentResponse createStudent(StudentCreateRequest studentCreateRequest) {

        Student student = studentMapper.toEntity(studentCreateRequest);

        if (studentRepository.existsByEmail(student.getEmail())) {

            throw new DuplicateResourceException("Student is already exist with email: " + student.getEmail());

        }

        if (userRepository.existsByEmail(studentCreateRequest.getEmail())){
            throw new DuplicateResourceException("User is already exists with email: "+studentCreateRequest.getEmail());
        }

        if (userRepository.existsByUsername(studentCreateRequest.getUsername())){
            throw new DuplicateResourceException("User is already exists with username "+studentCreateRequest.getUsername());

        }
        Role role = roleRepository.findByRoleName(AppRole.ROLE_STUDENT)
                .orElseThrow(()->new ResourceNotFoundException("Role not found "));
        User user = new User();

        user.setPassword(passwordEncoder.encode(studentCreateRequest.getPassword()));
        user.setUsername(studentCreateRequest.getUsername());
        user.setRoles(Set.of(role));
        user.setEnabled(true);
        user.setEmail(studentCreateRequest.getEmail());
        User savedUser = userRepository.save(user);

        student.setUser(savedUser);
        student.setStatus(StudentStatus.ACTIVE);
        Student savedStudent = studentRepository.save(student);


        return studentMapper.toResponse(savedStudent);
    }

    @Override
    public List<StudentResponse> getAllStudent() {

        List<Student> studentList = studentRepository.findAll();

        return studentList.stream().map(
                studentMapper::toResponse
        ).toList();
    }

    @Override
    public StudentResponse getStudentById(Long id) {
        Student student = getStudent(id);
        return studentMapper.toResponse(student);
    }


    @Override
    public StudentResponse updateStudent(StudentUpdateRequest studentUpdateRequest, Long id) {

        Student studentFromDb = getStudent(id);

        if (studentRepository.existsByEmailAndStudentIdNot(studentUpdateRequest.getEmail(), id)) {
            throw new DuplicateResourceException("Email already used by another student");
        }
        studentFromDb.setFirstname(studentUpdateRequest.getFirstname());
        studentFromDb.setLastname(studentUpdateRequest.getLastname());
        studentFromDb.setPhone(studentUpdateRequest.getPhone());
        studentFromDb.setEmail(studentUpdateRequest.getEmail());
        studentFromDb.setDateOfBirth(studentUpdateRequest.getDateOfBirth());


        return studentMapper.toResponse(studentRepository.save(studentFromDb));
    }

    @Override
    public StudentResponse updateStudentStatus(StudentStatusRequest studentStatusRequest, Long id) {
        Student student = getStudent(id);

        student.setStatus(studentStatusRequest.getStatus());

        return studentMapper.toResponse(studentRepository.save(student));
    }

    @Override
    public ApiMessageResponse deactivateStudent(Long id) {
        Student student = getStudent(id);
        student.setStatus(StudentStatus.INACTIVE);
        studentRepository.save(student);
        return new ApiMessageResponse("Student deactivated successfully");
    }

    @Override
    public StudentResponse getStudentCurrentProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Student student = studentRepository.findByUserUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found for current user"));
        return studentMapper.toResponse(student);
    }

    private Student getStudent(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID " + studentId));
    }
}

