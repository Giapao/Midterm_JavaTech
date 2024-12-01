package com.midterm.SpringCommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF vì ứng dụng RESTful
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // Công khai truy cập vào endpoint xác thực
                        .requestMatchers("/auth/**").permitAll() // Các endpoint như /auth/login, /auth/register
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Cho phép truy cập tài nguyên tĩnh
                        .requestMatchers(HttpMethod.GET, "/products").permitAll() // Ai cũng có thể GET /products
                        .requestMatchers(HttpMethod.POST, "/products").hasRole("ADMIN") // Chỉ ADMIN được phép POST vào /products
                        .anyRequest().authenticated() // Tất cả endpoint còn lại yêu cầu xác thực
                )

                // Stateless session cho RESTful API
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Thêm JwtFilter trước UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}