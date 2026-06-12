package com.sms.StudentManagmentSystem.grade;

import lombok.Getter;

@Getter
public enum GradeType {


    HOMEWORK(10),
    QUIZ(10),
    MIDTERM(20),
    FINAL_EXAM(30),
    PROJECT(25),
    PARTICIPATION(5);

    private final int maxScore;

     GradeType(int maxScore){
        this.maxScore=maxScore;
    }
}
