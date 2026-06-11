package com.sms.StudentManagmentSystem.security;

import com.sms.StudentManagmentSystem.auth.AppRole;
import com.sms.StudentManagmentSystem.auth.Role;
import com.sms.StudentManagmentSystem.auth.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) {
        for (AppRole role : AppRole.values()) {
            if (roleRepository.findByRoleName(role).isEmpty()) {
                roleRepository.save(new Role(null, role));
            }
        }
        System.out.println("Roller yükləndi: " + Arrays.toString(AppRole.values()));
    }
}