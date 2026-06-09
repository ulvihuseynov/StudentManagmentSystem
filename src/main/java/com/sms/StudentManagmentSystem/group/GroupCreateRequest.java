package com.sms.StudentManagmentSystem.group;


import com.sms.StudentManagmentSystem.course.Course;
import com.sms.StudentManagmentSystem.teacher.Teacher;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class GroupCreateRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Max(value = 20,message = "Group is full")
    @NotNull(message = "Capacity is not null")
    private Integer capacity;


}
