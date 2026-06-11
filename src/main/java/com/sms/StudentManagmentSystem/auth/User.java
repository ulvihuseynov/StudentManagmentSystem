package com.sms.StudentManagmentSystem.auth;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @ManyToMany
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> role=new HashSet<>();

    public User(String username, String email, @Nullable String encode) {
        this.username=username;
        this.email=email;
        this.password=encode;
    }
}
