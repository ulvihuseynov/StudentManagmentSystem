package com.sms.StudentManagmentSystem.student;

import com.sms.StudentManagmentSystem.exception.DuplicateResourceException;
import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    @Override
    public StudentResponse createStudent(StudentCreateRequest studentCreateRequest) {

        Student student = studentMapper.toEntity(studentCreateRequest);

        if (studentRepository.existsByEmail(student.getEmail())){

            throw new DuplicateResourceException("Student is already exist with email" + student.getEmail());

        }
        Student newStudent=new Student();

        newStudent.setFirstname(student.getFirstname());
        newStudent.setLastname(student.getLastname());
        newStudent.setEmail(student.getEmail());
        newStudent.setStatus(StudentStatus.ACTIVE);
        newStudent.setPhone(student.getPhone());
        newStudent.setDateOfBirth(student.getDateOfBirth());

    return studentMapper.toResponse(studentRepository.save(newStudent));
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
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID " + id));
        return studentMapper.toResponse(student);
    }


    @Override
    public StudentResponse updateStudent(StudentUpdateRequest studentUpdateRequest, Long id) {

        Student studentFromDb = getStudent(id);

        if (studentRepository.existsByEmailAndStudentIdNot(studentUpdateRequest.getEmail(),id)){
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

    private Student getStudent(Long studentId){
       return studentRepository.findById(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with ID " + studentId));
    }
}

