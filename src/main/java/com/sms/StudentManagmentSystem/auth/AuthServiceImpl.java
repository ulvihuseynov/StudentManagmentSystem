package com.sms.StudentManagmentSystem.auth;

import com.sms.StudentManagmentSystem.exception.BusinessException;
import com.sms.StudentManagmentSystem.exception.ResourceNotFoundException;
import com.sms.StudentManagmentSystem.payload.ApiMessageResponse;
import com.sms.StudentManagmentSystem.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public LoginResponse loginUser(LoginRequest loginRequest) {

        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
        String token = jwtUtils.getJwtFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        return new LoginResponse(userDetails.getId(), token, userDetails.getUsername(), userDetails.getEmail(), roles);

    }

    public ApiMessageResponse registerUser(RegisterRequest registerRequest) {

        boolean isUserName = userRepository.existsByUsername(registerRequest.getUsername());
        boolean isEmail = userRepository.existsByEmail(registerRequest.getEmail());
        if (isUserName) {
            throw new BusinessException("Error: Username is already exist: " + registerRequest.getUsername());
        }

        if (isEmail) {
            throw new BusinessException("Error: Email is already exist: " + registerRequest.getEmail());
        }


        User user = new User(registerRequest.getUsername(), registerRequest.getEmail(), passwordEncoder.encode(registerRequest.getPassword())

        );


        Role role = roleRepository.findByRoleName(AppRole.ROLE_STUDENT).orElseThrow(() -> new ResourceNotFoundException("Role not found "));
        user.setRoles(Set.of(role));
        userRepository.save(user);
        return new ApiMessageResponse("User successfully created");
    }

    public String currentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getName() != null) {

               return authentication.getName();
        }
        return null;
    }
}
