package com.sms.StudentManagmentSystem.enrollment;


import com.sms.StudentManagmentSystem.group.Group;
import com.sms.StudentManagmentSystem.student.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Enrollment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id",nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id",nullable = false)
    private Group group;

    @Column(nullable = false)
    private LocalDate enrollment_date;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status;

    @Column(nullable = false,updatable = false)
    @CreatedDate
    private LocalDateTime created_at;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updated_at;
}
