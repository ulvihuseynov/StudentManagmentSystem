package com.sms.StudentManagmentSystem.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;

    @Email
    @Column(nullable = false,unique = true)
    private String email;
    private String password;
    private boolean enabled;
    private Role role;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
