package com.salon_beaute.repository;

import com.salon_beaute.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // ✅ Trouver un utilisateur par email (unique)
    Optional<User> findByEmail(String email);

    // ✅ Trouver un utilisateur par téléphone (unique)
    Optional<User> findByPhone(String phone);

    // ✅ Récupérer les utilisateurs par rôle (CLIENT ou EMPLOYEE)
    List<User> findByRole(String role);
}
