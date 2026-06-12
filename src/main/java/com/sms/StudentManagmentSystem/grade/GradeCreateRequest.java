package com.sms.StudentManagmentSystem.grade;


import com.sms.StudentManagmentSystem.enrollment.Enrollment;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GradeCreateRequest {


    @NotBlank(message = "Title is required")
    private String title;
    @Min(value = 0,message = "Score must be at least 0")
    @Max(value = 10,message = "Score must be at most 10")
    @NotNull(message = "Score is required")
    private Integer score;

    @Min(value = 0,message = "Max score must be at least 0")
    @Max(value = 100,message = "Score must be at least 100")
    @NotNull(message = "Max score is required")
    private Integer maxScore;

    @NotNull(message = "Grade type id is required")
    private GradeType gradeType;

    @NotNull(message = "Grade date is required")
    private LocalDate gradeDate;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Enrollment id is required")
    private Long enrollmentId;
    
}
