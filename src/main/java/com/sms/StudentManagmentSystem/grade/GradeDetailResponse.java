package com.sms.StudentManagmentSystem.grade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GradeDetailResponse {

    private GradeType gradeType;
    private Integer score;
    private Integer maxScore;
}
