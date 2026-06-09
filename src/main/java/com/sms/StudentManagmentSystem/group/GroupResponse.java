package com.sms.StudentManagmentSystem.group;


import com.sms.StudentManagmentSystem.course.Course;
import com.sms.StudentManagmentSystem.course.CourseResponse;
import com.sms.StudentManagmentSystem.teacher.Teacher;
import com.sms.StudentManagmentSystem.teacher.TeacherResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponse {



    private Long groupId;
    private String name;
    private CourseResponse course;
    private TeacherResponse teacher;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer capacity;
    private GroupStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
