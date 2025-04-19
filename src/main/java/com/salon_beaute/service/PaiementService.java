package com.salon_beaute.service;

import com.salon_beaute.model.Paiement;
import com.salon_beaute.repository.PaiementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    // 🔹 Enregistrer un paiement
    public Paiement savePaiement(Paiement paiement) {
        return paiementRepository.save(paiement);
    }

    // 🔹 Récupérer tous les paiements
    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }

    // 🔹 Récupérer un paiement par ID
    public Optional<Paiement> getPaiementById(Long id) {
        return paiementRepository.findById(id);
    }

    // 🔹 Récupérer les paiements par email
    public List<Paiement> getPaiementsByClientEmail(String email) {
        return paiementRepository.findByClientEmail(email);
    }

    // 🔹 Mettre à jour le statut d’un paiement (après confirmation Stripe)
    public Paiement updateStatutPaiement(Long id, String statut) {
        Optional<Paiement> optionalPaiement = paiementRepository.findById(id);
        if (optionalPaiement.isPresent()) {
            Paiement paiement = optionalPaiement.get();
            paiement.setStatut(statut);
            return paiementRepository.save(paiement);
        } else {
            throw new RuntimeException("Paiement non trouvé avec ID: " + id);
        }
    }

    // 🔹 Supprimer un paiement
    public void deletePaiement(Long id) {
        paiementRepository.deleteById(id);
    }
}
