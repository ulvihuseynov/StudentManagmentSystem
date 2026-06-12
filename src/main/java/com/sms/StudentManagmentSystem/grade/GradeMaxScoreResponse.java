package com.sms.StudentManagmentSystem.grade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GradeMaxScoreResponse {

    private Long enrollmentId;
    private Integer totalScore;
    private Integer totalMaxScore;
    private double percentage;
    List<GradeType> gradeTypeList = new ArrayList<>();
}
