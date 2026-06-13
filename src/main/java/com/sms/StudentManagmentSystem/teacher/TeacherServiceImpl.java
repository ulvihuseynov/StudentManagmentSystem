package com.sms.StudentManagmentSystem.teacher;

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
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeacherMapper teacherMapper;

    @Override
    public TeacherResponse createTeacher(TeacherCreateRequest teacherCreateRequest) {

        Teacher teacher = teacherMapper.toEntity(teacherCreateRequest);

        if (teacherRepository.existsByEmail(teacher.getEmail())) {
            throw new DuplicateResourceException("Teacher is already exist with email: " + teacherCreateRequest.getEmail());
        }

        if (userRepository.existsByEmail(teacherCreateRequest.getEmail())){
            throw new DuplicateResourceException("User is already exists with email "+teacherCreateRequest.getEmail());
        }

        if (userRepository.existsByUsername(teacherCreateRequest.getUsername())){
            throw new DuplicateResourceException("User is already exists with username "+teacherCreateRequest.getUsername());
        }
        Role role = roleRepository.findByRoleName(AppRole.ROLE_TEACHER)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));


        User user = new User();

        user.setPassword(passwordEncoder.encode(teacherCreateRequest.getPassword()));
        user.setUsername(teacherCreateRequest.getUsername());
        user.setRoles(Set.of(role));
        user.setEnabled(true);
        user.setEmail(teacherCreateRequest.getEmail());
        User savedUser = userRepository.save(user);

        teacher.setUser(savedUser);
        teacher.setStatus(TeacherStatus.ACTIVE);
        Teacher savedTeacher = teacherRepository.save(teacher);


        return teacherMapper.toResponse(savedTeacher);
    }

    @Override
    public List<TeacherResponse> getAllTeacher() {
        List<Teacher> teacherList = teacherRepository.findAll();
        return teacherList.stream().map(teacherMapper::toResponse).toList();
    }

    @Override
    public TeacherResponse getTeacherById(Long id) {

        Teacher teacher = getTeacher(id);
        return teacherMapper.toResponse(teacher);
    }

    @Override
    public TeacherResponse updateTeacher(TeacherUpdateRequest teacherUpdateRequest, Long id) {

        Teacher teacherFromDb = getTeacher(id);
        if (teacherRepository.existsByEmailAndTeacherIdNot(teacherUpdateRequest.getEmail(), id)) {
            throw new DuplicateResourceException("Email already used by another teacher: " + teacherUpdateRequest.getEmail());
        }
        teacherFromDb.setFirstname(teacherUpdateRequest.getFirstname());
        teacherFromDb.setLastname(teacherUpdateRequest.getLastname());
        teacherFromDb.setEmail(teacherUpdateRequest.getEmail());
        teacherFromDb.setPhone(teacherUpdateRequest.getPhone());
        teacherFromDb.setSpecialization(teacherUpdateRequest.getSpecialization());


        return teacherMapper.toResponse(teacherRepository.save(teacherFromDb));
    }

    @Override
    public TeacherResponse updateStatus(TeacherStatusRequest teacherStatusRequest, Long id) {

        Teacher teacherFromDb = getTeacher(id);
        teacherFromDb.setStatus(teacherStatusRequest.getStatus());

        return teacherMapper.toResponse(teacherRepository.save(teacherFromDb));
    }

    @Override
    public ApiMessageResponse deactivateTeacher(Long id) {

        Teacher teacher = getTeacher(id);

        teacher.setStatus(TeacherStatus.INACTIVE);

        teacherRepository.save(teacher);

        return new ApiMessageResponse("Teacher deactivated successfully");
    }

    @Override
    public TeacherResponse getTeacherCurrentProfile() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Teacher teacher = teacherRepository.findByUserUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher profile not found for current user"));
        return  teacherMapper.toResponse(teacher);
    }


    private Teacher getTeacher(Long id) {

        return teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with ID: " + id));
    }
}
