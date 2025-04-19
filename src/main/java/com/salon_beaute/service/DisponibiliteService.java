package com.salon_beaute.service;

import com.salon_beaute.model.Disponibilite;
import com.salon_beaute.repository.DisponibiliteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DisponibiliteService {

    @Autowired
    private DisponibiliteRepository disponibiliteRepository;

    public Disponibilite ajouterDisponibilite(Disponibilite disponibilite) {
        Optional<Disponibilite> existante = disponibiliteRepository.findByEmployeIdAndDateAndHeureDebutAndHeureFin(
                disponibilite.getEmployeId(),
                disponibilite.getDate(),
                disponibilite.getHeureDebut(),
                disponibilite.getHeureFin()
        );

        if (existante.isPresent()) {
            Disponibilite dispoExistante = existante.get();
            dispoExistante.setDisponible(disponibilite.isDisponible()); // ✅ On met disponible = true
            return disponibiliteRepository.save(dispoExistante); // ✅ On update
        }

        return disponibiliteRepository.save(disponibilite); // ✅ Sinon on insère
    }


    public List<Disponibilite> getDisponibilitesParEmploye(Long employeId) {
        return disponibiliteRepository.findByEmployeId(employeId);
    }

    public List<Disponibilite> getDisponibilitesParDate(LocalDate date) {
        return disponibiliteRepository.findByDate(date);
    }

    public List<Disponibilite> getDisponibilitesDisponiblesParDate(LocalDate date) {
        return disponibiliteRepository.findByDateAndDisponibleTrue(date);
    }

    public void supprimerDisponibilite(Long id) {
        disponibiliteRepository.deleteById(id);
    }

    public List<Disponibilite> saveAll(List<Disponibilite> disponibilites) {
        for (int i = 0; i < disponibilites.size(); i++) {
            Disponibilite dispo = disponibilites.get(i);

            Optional<Disponibilite> existante = disponibiliteRepository.findByEmployeIdAndDateAndHeureDebutAndHeureFin(
                    dispo.getEmployeId(),
                    dispo.getDate(),
                    dispo.getHeureDebut(),
                    dispo.getHeureFin()
            );

            if (existante.isPresent()) {
                Disponibilite existanteDispo = existante.get();

                // ❌ Ne pas modifier si déjà réservé
                if (existanteDispo.isBooked()) {
                    continue;
                }

                // ✅ Mise à jour du statut "disponible"
                existanteDispo.setDisponible(dispo.isDisponible());
                disponibilites.set(i, existanteDispo);
            }
        }

        return disponibiliteRepository.saveAll(disponibilites);
    }

    public Optional<Disponibilite> getById(Long id) {
        return disponibiliteRepository.findById(id);
    }
}
