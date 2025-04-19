package com.salon_beaute.repository;

import com.salon_beaute.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByClientEmail(String email);
}

