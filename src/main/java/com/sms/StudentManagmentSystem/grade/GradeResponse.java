package com.sms.StudentManagmentSystem.grade;


import com.sms.StudentManagmentSystem.enrollment.EnrollmentResponse;
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
public class GradeResponse {


    private Long gradeId;
    private String title;
    private Integer score;
    private Integer maxScore;
    private GradeType gradeType;
    private LocalDate gradeDate;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private EnrollmentResponse enrollment;

}
