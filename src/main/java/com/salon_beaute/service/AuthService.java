package com.salon_beaute.service;

import com.salon_beaute.exception.BusinessException;
import com.salon_beaute.model.User;
import com.salon_beaute.repository.UserRepository;
import com.salon_beaute.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(User user) {
        // 🔍 Vérifie l’unicité de l’email
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cet email est déjà utilisé.");
        }

        // 🔍 Vérifie l’unicité du numéro de téléphone
        if (userRepository.findByPhone(user.getPhone()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ce numéro est déjà utilisé.");
        }

        // 🔐 Hash du mot de passe
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 💾 Sauvegarde en base
        userRepository.save(user);

        // 🔐 Génération du token JWT
        return jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getName());
    }


    // 🔹 Connexion (Login)
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!password.equals(user.getPhone())) {
            throw new RuntimeException("Numero de telephone incorrect");
        }

        if (user.getStatus().equals("bloqué")) {
            throw new RuntimeException("Utilisateur bloqué");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole(), user.getName()); // ✅ Passe le rôle
    }
}
