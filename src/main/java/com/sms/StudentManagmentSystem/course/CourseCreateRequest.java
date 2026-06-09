package com.sms.StudentManagmentSystem.course;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    private Integer durationInMonths;

    @Min(value = 0,message = "Price is not negative")
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private CourseLevel level;

    @Enumerated(EnumType.STRING)
    private CourseStatus status;

}
