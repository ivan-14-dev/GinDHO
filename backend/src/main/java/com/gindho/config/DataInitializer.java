package com.gindho.config;

import com.gindho.model.Role;
import com.gindho.model.User;
import com.gindho.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@gindho.com").isEmpty()) {
            User admin = User.builder()
                    .nom("Admin")
                    .prenom("Systeme")
                    .email("admin@gindho.com")
                    .motDePasseHash(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .actif(true)
                    .build();
            userRepository.save(admin);
            System.out.println("Admin user created: admin@gindho.com / admin123");
        }
    }
}