package com.sms.StudentManagmentSystem.security;

import com.sms.StudentManagmentSystem.auth.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthTokenFilter authTokenFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;
    private final UserDetailsServiceImpl userDetailsService;


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) {

        return security.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(header -> header.frameOptions(
                        HeadersConfigurer.FrameOptionsConfig::sameOrigin))
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler)
                )
                .authorizeHttpRequests(request ->
                        request
                                .requestMatchers(HttpMethod.GET, "/api/auth/me").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/courses/**").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                                .requestMatchers(HttpMethod.GET, "/api/groups/**").hasAnyRole("ADMIN", "TEACHER", "STUDENT")
                                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()

                                .requestMatchers(HttpMethod.GET, "/api/students/**").hasAnyRole("ADMIN","STUDENT")
                                .requestMatchers(HttpMethod.GET, "/api/teachers/**").hasAnyRole("ADMIN","TEACHER")
                                .requestMatchers(HttpMethod.GET, "/api/enrollments/**").hasRole("ADMIN")

                                .requestMatchers("/api/students/**").hasRole("ADMIN")
                                .requestMatchers("/api/teachers/**").hasRole("ADMIN")
                                .requestMatchers("/api/courses/**").hasRole("ADMIN")
                                .requestMatchers("/api/groups/**").hasRole("ADMIN")
                                .requestMatchers("/api/enrollments/**").hasRole("ADMIN")
                                .requestMatchers("/api/grades/**").hasAnyRole("ADMIN","TEACHER")
                                .requestMatchers("/api/attendance/**").hasAnyRole("ADMIN", "TEACHER").
                                anyRequest().authenticated())
                .authenticationProvider(authenticationProvider())

                .addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build()
                ;

    }


}
