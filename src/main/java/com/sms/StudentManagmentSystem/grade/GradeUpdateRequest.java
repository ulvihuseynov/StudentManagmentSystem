package com.sms.StudentManagmentSystem.grade;


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
public class GradeUpdateRequest {

    @NotBlank(message = "Title is required")
    private String title;
    @Min(value = 0,message = "Score must be at least 0")
    @NotNull(message = "Score is required")
    private Integer score;

    @NotNull(message = "Grade date is required")
    private LocalDate gradeDate;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Enrollment id is required")
    private Long enrollmentId;
    
}
