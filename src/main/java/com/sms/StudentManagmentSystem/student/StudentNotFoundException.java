package com.sms.StudentManagmentSystem.student;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class StudentNotFoundException extends RuntimeException{

    private String message;

    public StudentNotFoundException(String message){
        super(message);
        this.message=message;

    }
}
