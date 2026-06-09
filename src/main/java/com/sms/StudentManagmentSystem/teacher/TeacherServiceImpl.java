package com.sms.StudentManagmentSystem.teacher;

import com.sms.StudentManagmentSystem.exception.DuplicateResourceException;
import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService{

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Override
    public TeacherResponse createTeacher(TeacherCreateRequest teacherCreateRequest) {

        Teacher teacher = teacherMapper.toEntity(teacherCreateRequest);

        if (teacherRepository.existsByEmail(teacher.getEmail())){
            throw new DuplicateResourceException("Teacher is already exist with email: " + teacher.getEmail());
        }
        Teacher newTeacher=new Teacher();

        newTeacher.setFirstname(teacher.getFirstname());
        newTeacher.setLastname(teacher.getLastname());
        newTeacher.setEmail(teacher.getEmail());
        newTeacher.setPhone(teacher.getPhone());
        newTeacher.setStatus(TeacherStatus.ACTIVE);
        newTeacher.setSpecialization(teacher.getSpecialization());


        return teacherMapper.toResponse(teacherRepository.save(newTeacher));
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
        if (teacherRepository.existsByEmailAndTeacherIdNot(teacherUpdateRequest.getEmail(),id)){
            throw new DuplicateResourceException("Email already used by another teacher: " + teacherFromDb.getEmail());
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


    private Teacher getTeacher(Long id){

        return teacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher not found with ID: " + id));
    }
}
