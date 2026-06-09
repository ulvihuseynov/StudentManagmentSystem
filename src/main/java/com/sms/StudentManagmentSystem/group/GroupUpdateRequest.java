package com.sms.StudentManagmentSystem.group;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupUpdateRequest {

    @NotBlank(message = "Name is required")
    private String name;


    @Min(value = 1, message = "Capacity must be at least 1")
    @Max(value = 20, message = "Capacity cannot be greater than 20")
    @NotNull(message = "Capacity is not null")
    private Integer capacity;

    @NotNull(message = "Course id is not null")
    private Long courseId;

    @NotNull(message = "Teacher id is not null")
    private Long teacherId;

    @NotNull(message = "Start date is not null")
    private LocalDate startDate;

    @NotNull(message = "End date is not null")
    private LocalDate endDate;
}
