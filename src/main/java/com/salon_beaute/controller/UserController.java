package com.salon_beaute.controller;

import com.salon_beaute.model.User;
import com.salon_beaute.model.request.ChangePasswordRequest;
import com.salon_beaute.model.request.UserInfosRequest;
import com.salon_beaute.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Récupérer tous les utilisateurs
    @GetMapping("/all")
    public List<User> getTousLesUtilisateurs() {
        return userService.getTousLesUtilisateurs();
    }

    // ✅ Récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public Optional<User> getUtilisateurParId(@PathVariable Long id) {
        return userService.getUtilisateurParId(id);
    }

    // ✅ Récupérer un utilisateur par email
    @GetMapping("/email/{email}")
    public Optional<User> getUtilisateurParEmail(@PathVariable String email) {
        return userService.getUtilisateurParEmail(email);
    }

    // ✅ Ajouter un utilisateur (client ou employé)
    @PostMapping("/add")
    public User ajouterUtilisateur(@RequestBody User user) {
        return userService.creerUtilisateur(user);
    }

    // ✅ Mettre à jour un utilisateur complet
    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    // ✅ Mettre à jour uniquement le statut d'un utilisateur
    @PatchMapping("/update/{id}/status")
    public User updateUserStatus(@PathVariable Long id, @RequestParam String status) {
        return userService.updateUserStatus(id, status);
    }

    // ✅ Supprimer un utilisateur
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    // ✅ Filtrer les utilisateurs par rôle (client/employé)
    @GetMapping("/role/{role}")
    public List<User> getUsersByRole(@PathVariable String role) {
        return userService.getUsersByRole(role);
    }

    // ✅ Changer le mot de passe d'un utilisateur
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
        Optional<User> optionalUser = userService.getUtilisateurParEmail(request.getEmail());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Utilisateur non trouvé.");
        }

        User user = optionalUser.get();

        // Vérifier mot de passe actuel
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("❌ Mot de passe actuel incorrect.");
        }

        if (request.getNewPassword() == null || request.getNewPassword().length() < 6) {
            return ResponseEntity.badRequest().body("⚠️ Le nouveau mot de passe doit contenir au moins 6 caractères.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.updateUser(user.getId(), user);
        return ResponseEntity.ok("✅ Mot de passe mis à jour avec succès !");
    }

    // Ajuster les informations personnelles d'un utilisateur (nom, email, téléphone)
    @PutMapping("/update-info/{id}")
    public ResponseEntity<String> updateUserInfos(@PathVariable Long id, @RequestBody UserInfosRequest updatedUser) {
        Optional<User> optionalUser = userService.getUtilisateurParId(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Utilisateur non trouvé.");
        }

        User user = optionalUser.get();
        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPhone(updatedUser.getPhone());

        userService.updateUser(id, user);
        return ResponseEntity.ok("✅ Informations mises à jour avec succès !");
    }

}
