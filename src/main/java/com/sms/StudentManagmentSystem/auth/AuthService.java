package com.sms.StudentManagmentSystem.auth;

import com.sms.StudentManagmentSystem.exception.BusinessException;
import com.sms.StudentManagmentSystem.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public LoginResponse loginUser(LoginRequest loginRequest) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserDetailsImpl userDetails =(UserDetailsImpl) authenticate.getPrincipal();
        String token = jwtUtils.getJwtFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream().map(
                GrantedAuthority::getAuthority).toList();


        return new LoginResponse(userDetails.getId(),token,userDetails.getUsername(),userDetails.getEmail(),roles);

    }

    public LoginResponse registerUser(RegisterRequest registerRequest) {

        boolean isUserName=userRepository.existsByUsername(registerRequest.getUsername());
        boolean isEmail=userRepository.existsByEmail(registerRequest.getEmail());
        if (isUserName){
            throw new BusinessException("uSER var");
        }

        if (isEmail){
            throw new BusinessException("email var");
        }

        User user=new User(
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                passwordEncoder.encode(registerRequest.getPassword())

        );

        Set<String> role = registerRequest.getRole();
        Set<Role> roleSet=new HashSet<>();


    }
}
