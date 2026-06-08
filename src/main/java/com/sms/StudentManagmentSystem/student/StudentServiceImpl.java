package com.sms.StudentManagmentSystem.student;

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
        Student studentDb=studentRepository.findByFirstname(student.getFirstname());
        if (studentDb !=null){

            throw new StudentNotFoundException("Student is already exist " + studentDb.getFirstname());

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
        if (studentList.isEmpty()){
            throw new StudentNotFoundException("The student has not been added yet.");
        }
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
}

