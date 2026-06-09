package com.sms.StudentManagmentSystem.course;

import com.sms.StudentManagmentSystem.exception.DuplicateResourceException;
import com.sms.StudentManagmentSystem.exception.ResourceNotFoundException;
import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService{

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Override
    public CourseResponse createCourse(CourseCreateRequest courseCreateRequest) {

        Course course = courseMapper.toEntity(courseCreateRequest);

        if (courseRepository.existsByName(course.getName())){
            throw new DuplicateResourceException("Course is already exist with name "+course.getName());
        }

        course.setStatus(CourseStatus.ACTIVE);

        return courseMapper.toResponse(courseRepository.save(course));
    }

    @Override
    public List<CourseResponse> getAllCourse() {

        List<Course> courseList = courseRepository.findAll();
        return courseList.stream().map(courseMapper::toResponse).toList();
    }

    @Override
    public CourseResponse getCourseById(Long id) {
        Course course = getCourse(id);
        return courseMapper.toResponse(course);
    }

    @Override
    public CourseResponse updateCourse(CourseUpdateRequest courseUpdateRequest, Long id) {
        Course course = getCourse(id);

        if (courseRepository.existsByNameAndCourseIdNot(courseUpdateRequest.getName(),id)){
            throw new DuplicateResourceException("Name  already used by another course: "+courseUpdateRequest.getName());
        }

        course.setName(courseUpdateRequest.getName());
        course.setPrice(courseUpdateRequest.getPrice());
        course.setLevel(courseUpdateRequest.getLevel());
        course.setDescription(courseUpdateRequest.getDescription());
        course.setDescription(courseUpdateRequest.getDescription());

        return courseMapper.toResponse(courseRepository.save(course));
    }

    @Override
    public CourseResponse updateCourseStatus(CourseUpdateStatus courseUpdateStatus, Long id) {
        Course course = getCourse(id);
        course.setStatus(courseUpdateStatus.getStatus());

        return courseMapper.toResponse(courseRepository.save(course));
    }

    @Override
    public ApiMessageResponse deactivateCourse(Long id) {

        Course course = getCourse(id);
        course.setStatus(CourseStatus.ARCHIVED);
        courseRepository.save(course);
        return new ApiMessageResponse("Course successfully archived");
    }

    private Course getCourse(Long id){
        return courseRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Course not found with id: "+id));
    }
}
