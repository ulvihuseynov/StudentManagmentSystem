package com.sms.StudentManagmentSystem.group;


import com.sms.StudentManagmentSystem.course.CourseResponse;
import com.sms.StudentManagmentSystem.teacher.TeacherResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDate;
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
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer capacity;
    private GroupStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
