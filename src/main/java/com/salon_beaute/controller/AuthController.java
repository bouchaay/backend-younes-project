package com.salon_beaute.controller;

import com.salon_beaute.model.request.LoginRequest;
import com.salon_beaute.model.User;
import com.salon_beaute.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 🔹 Inscription
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        String token = authService.register(user);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPhone();
        String token = authService.login(email, password);
        return ResponseEntity.ok(Collections.singletonMap("token", token));
    }
}
