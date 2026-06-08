package com.sms.StudentManagmentSystem.exception;

import com.sms.StudentManagmentSystem.student.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleNotValidException(MethodArgumentNotValidException ex){

        Map<String,String> errorResponse=new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(
                error->{
                    errorResponse.put("Field",error.getField());
                    errorResponse.put("Message",error.getDefaultMessage());
                }
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleStudentNotFoundException(StudentNotFoundException ex){

        ApiErrorResponse errorResponse=new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(RuntimeException ex){

        ApiErrorResponse errorResponse=new ApiErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
