package com.sms.StudentManagmentSystem.group;

import com.sms.StudentManagmentSystem.course.Course;
import com.sms.StudentManagmentSystem.teacher.Teacher;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupUpdateRequest {

    @NotBlank(message = "Name is required")
    private String name;


    @Max(value = 20,message = "Group is full")
    @NotNull(message = "Capacity is not null")
    private Integer capacity;
}
