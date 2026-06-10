package com.sms.StudentManagmentSystem.group;

import com.sms.StudentManagmentSystem.course.Course;
import com.sms.StudentManagmentSystem.course.CourseRepository;
import com.sms.StudentManagmentSystem.course.CourseStatus;
import com.sms.StudentManagmentSystem.exception.BusinessException;
import com.sms.StudentManagmentSystem.exception.ResourceNotFoundException;
import com.sms.StudentManagmentSystem.teacher.Teacher;
import com.sms.StudentManagmentSystem.teacher.TeacherRepository;
import com.sms.StudentManagmentSystem.teacher.TeacherStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final GroupMapper groupMapper;

    @Override
    public GroupResponse createGroup(GroupCreateRequest groupCreateRequest) {

        Group group = groupMapper.toEntity(groupCreateRequest);

        Course course = courseRepository.findById(groupCreateRequest.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + groupCreateRequest.getCourseId()));

        Teacher teacher = teacherRepository.findById(groupCreateRequest.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + groupCreateRequest.getTeacherId()));

        validateCourseAndTeacher(teacher, course);
        validateGroupDates(groupCreateRequest.getStartDate(), groupCreateRequest.getEndDate());

        group.setCourse(course);
        group.setTeacher(teacher);

        group.setStatus(GroupStatus.PLANNED);

        group.setCapacity(groupCreateRequest.getCapacity());
        return groupMapper.toResponse(groupRepository.save(group));
    }


    @Override
    public List<GroupResponse> getAllGroup() {
        List<Group> groupList = groupRepository.findAll();
        return groupList.stream().map(groupMapper::toResponse).toList();
    }

    @Override
    public GroupResponse getGroupById(Long id) {
        Group group = getGroup(id);
        return groupMapper.toResponse(group);
    }

    @Override
    public GroupResponse updateGroup(GroupUpdateRequest groupUpdateRequest, Long id) {

        Group groupFromDb = getGroup(id);
        Course course = courseRepository.findById(groupUpdateRequest.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + groupUpdateRequest.getCourseId()));

        Teacher teacher = teacherRepository.findById(groupUpdateRequest.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + groupUpdateRequest.getTeacherId()));


        validateCourseAndTeacher(teacher,course);
        validateGroupDates(groupUpdateRequest.getStartDate(),groupUpdateRequest.getEndDate());

        groupFromDb.setTeacher(teacher);
        groupFromDb.setCourse(course);
        groupFromDb.setCapacity(groupUpdateRequest.getCapacity());
        groupFromDb.setName(groupUpdateRequest.getName());
        groupFromDb.setStartDate(groupUpdateRequest.getStartDate());
        groupFromDb.setEndDate(groupUpdateRequest.getEndDate());
        return groupMapper.toResponse(groupRepository.save(groupFromDb));
    }

    @Override
    public GroupResponse updateGroupStatus(GroupUpdateStatus groupUpdateStatus, Long id) {
        Group group = getGroup(id);
        group.setStatus(groupUpdateStatus.getStatus());
        groupRepository.save(group);
        return groupMapper.toResponse(group);
    }

    private Group getGroup(Long id) {

        return groupRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found with ID: " + id));
    }

    private void validateCourseAndTeacher(Teacher teacher, Course course) {

        if (teacher.getStatus() != TeacherStatus.ACTIVE) {
            throw new BusinessException("Only ACTIVE teacher can be used for group");
        }

        if (course.getStatus() != CourseStatus.ACTIVE) {
            throw new BusinessException("Only ACTIVE course can be assigned to group");
        }
    }
    private void validateGroupDates(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)){
            throw new BusinessException("End date cannot be before start date");
        }
    }

}
