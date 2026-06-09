package com.sms.StudentManagmentSystem.course;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourseCreateRequest {



    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description name is required")
    @Size(min = 3 ,message = "Description must be at least 3 characters")
    private String description;

    @Min(value = 1,message = "Duration In Months at least 1 month")
    @NotNull(message = "Duration In Month is not null")
    private Integer durationInMonths;

    @DecimalMin(value = "0.0",message = "Price is not negative")
    @NotNull(message = "Price is not null")
    private BigDecimal price;

    @NotNull(message = "Level is not null")
    private CourseLevel level;



}
