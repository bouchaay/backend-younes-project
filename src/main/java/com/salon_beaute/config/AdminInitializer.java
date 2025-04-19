package com.salon_beaute.config;

import com.salon_beaute.model.User;
import com.salon_beaute.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String adminEmail = "admin2@salon.com";

            // Vérifier si l'admin existe déjà
            if (userRepository.findByEmail(adminEmail).isEmpty()) {
                User admin = new User();
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode("admin123")); // 🔒 Mot de passe hashé
                admin.setRole("admin");
                admin.setName("admin");
                admin.setStatus("actif");
                admin.setPhone("0123456789");

                userRepository.save(admin);
                System.out.println("✅ Admin créé avec succès !");
            } else {
                System.out.println("ℹ️ Admin déjà existant.");
            }
        };
    }
}
