package com.sms.StudentManagmentSystem.group;

import com.sms.StudentManagmentSystem.course.Course;
import com.sms.StudentManagmentSystem.course.CourseRepository;
import com.sms.StudentManagmentSystem.exception.ResourceNotFoundException;
import com.sms.StudentManagmentSystem.teacher.Teacher;
import com.sms.StudentManagmentSystem.teacher.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService{

    private final GroupRepository groupRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final GroupMapper groupMapper;

    @Override
    public GroupResponse createGroup(GroupCreateRequest groupCreateRequest, Long courseId, Long teacherId) {

        Group group = groupMapper.toEntity(groupCreateRequest);

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + teacherId));

        group.setStatus(GroupStatus.ACTIVE);
        group.setTeacher(teacher);
        group.setCourse(course);
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
    public GroupResponse updateGroup(GroupUpdateRequest groupUpdateRequest, Long id, Long courseId, Long teacherId) {

        Group groupFromDb = getGroup(id);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + courseId));

        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id: " + teacherId));

        groupFromDb.setTeacher(teacher);
        groupFromDb.setCourse(course);
        groupFromDb.setCapacity(groupUpdateRequest.getCapacity());
        groupFromDb.setName(groupUpdateRequest.getName());

        return groupMapper.toResponse(groupRepository.save(groupFromDb));
    }

    @Override
    public GroupResponse updateGroupStatus(GroupUpdateStatus groupUpdateStatus, Long id) {
        Group group = getGroup(id);
        group.setStatus(groupUpdateStatus.getStatus());
        groupRepository.save(group);
        return groupMapper.toResponse(group);
    }

    private Group getGroup(Long id){

        return groupRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Group not found with ID: "+id));
    }
}
