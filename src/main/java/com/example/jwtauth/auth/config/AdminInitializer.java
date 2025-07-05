package com.example.jwtauth.auth.config;

import com.example.jwtauth.auth.domain.entity.User;
import com.example.jwtauth.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // 주입받기

    @Bean
    public CommandLineRunner initAdmin() {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {
                String rawPassword = "1234";
                String encodedPassword = passwordEncoder.encode(rawPassword);
                User admin = User.createAdmin(
                        "admin",
                        encodedPassword,
                        "관리자"
                );
                userRepository.save(admin);
            }
        };
    }
}

