package com.sms.StudentManagmentSystem.security;

import com.sms.StudentManagmentSystem.auth.AppRole;
import com.sms.StudentManagmentSystem.auth.Role;
import com.sms.StudentManagmentSystem.auth.RoleRepository;
import com.sms.StudentManagmentSystem.auth.User;
import com.sms.StudentManagmentSystem.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {

        for (AppRole role : AppRole.values()) {
            if (roleRepository.findByRoleName(role).isEmpty()) {
                roleRepository.save(new Role(null, role));
            }
        }

        createTestUserIfNotExists(
                "admin",
                "admin@gmail.com",
                "admin123",
                AppRole.ROLE_ADMIN
        );

        createTestUserIfNotExists(
                "teacher",
                "teacher@gmail.com",
                "teacher123",
                AppRole.ROLE_TEACHER
        );

        System.out.println("Roller yükləndi: " + Arrays.toString(AppRole.values()));
        System.out.println("Test user-lər yoxlanıldı/yükləndi: admin, teacher");
    }

    private void createTestUserIfNotExists(
            String username,
            String email,
            String rawPassword,
            AppRole appRole
    ) {
        if (userRepository.existsByUsername(username)) {
            return;
        }

        Role role = roleRepository.findByRoleName(appRole)
                .orElseThrow(() -> new RuntimeException("Role not found: " + appRole));

        User user = new User(
                username,
                email,
                passwordEncoder.encode(rawPassword)
        );

        user.setEnabled(true);
        user.setRoles(new HashSet<>(Set.of(role)));

        userRepository.save(user);
    }
}