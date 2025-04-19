package com.salon_beaute.service;

import com.salon_beaute.model.User;
import com.salon_beaute.repository.AppointmentRepository;
import com.salon_beaute.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ✅ Créer un utilisateur (avec vérification d'unicité email/téléphone)
    public User creerUtilisateur(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Cet email est déjà utilisé !");
        }
        if (userRepository.findByPhone(user.getPhone()).isPresent()) {
            throw new RuntimeException("Ce numéro de téléphone est déjà utilisé !");
        }

        // Hasher le mot de passe avant de sauvegarder
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // ✅ Récupérer tous les utilisateurs
    public List<User> getTousLesUtilisateurs() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            int completed = 0;
            int canceled = 0;

            if (user.getRole().equalsIgnoreCase("employee")) {
                completed = appointmentRepository.countByEmployeeNameAndStatus(user.getName(), "Terminé");
                canceled = appointmentRepository.countByEmployeeNameAndStatus(user.getName(), "Annulé");
            } else if (user.getRole().equalsIgnoreCase("client")) {
                completed = appointmentRepository.countByClientEmailAndStatus(user.getEmail(), "Terminé");
                canceled = appointmentRepository.countByClientEmailAndStatus(user.getEmail(), "Annulé");
            }

            user.setCompletedAppointments(completed);
            user.setCanceledAppointments(canceled);
        }

        userRepository.saveAll(users); // Sauvegarde des statistiques mises à jour
        return users;
    }



    // ✅ Récupérer un utilisateur par ID
    public Optional<User> getUtilisateurParId(Long id) {
        return userRepository.findById(id);
    }

    // ✅ Récupérer un utilisateur par email
    public Optional<User> getUtilisateurParEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // ✅ Mettre à jour un utilisateur complet
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            if (!updatedUser.getPassword().startsWith("$2a$")) {
                user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            } else {
                user.setPassword(updatedUser.getPassword());
            }
            user.setPhone(updatedUser.getPhone());
            user.setRole(updatedUser.getRole());
            user.setStatus(updatedUser.getStatus());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé !"));
    }

    // ✅ Mettre à jour uniquement le statut d'un utilisateur
    public User updateUserStatus(Long id, String newStatus) {
        return userRepository.findById(id).map(user -> {
            user.setStatus(newStatus);
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé !"));
    }

    // ✅ Supprimer un utilisateur
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Utilisateur non trouvé !");
        }
        userRepository.deleteById(id);
    }

    // ✅ Filtrer les utilisateurs par rôle
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

}
