package com.salon_beaute.controller;

import com.salon_beaute.model.Disponibilite;
import com.salon_beaute.service.DisponibiliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/disponibilites")
public class DisponibiliteController {

    @Autowired
    private DisponibiliteService disponibiliteService;

    // ✅ Ajouter une disponibilité
    @PostMapping("/add")
    public Disponibilite ajouterDisponibilite(@RequestBody Disponibilite disponibilite) {
        return disponibiliteService.ajouterDisponibilite(disponibilite);
    }

    // ✅ Récupérer les dispos d’un employé
    @GetMapping("/employee/{id}")
    public List<Disponibilite> getParEmploye(@PathVariable Long id) {
        return disponibiliteService.getDisponibilitesParEmploye(id);
    }

    // ✅ Récupérer les dispos d’une date
    @GetMapping("/date/{date}")
    public List<Disponibilite> getParDate(@PathVariable String date) {
        return disponibiliteService.getDisponibilitesParDate(LocalDate.parse(date));
    }

    // ✅ Récupérer les dispos *actives* (disponible = true) pour une date
    @GetMapping("/available")
    public List<Disponibilite> getDisponibles(@RequestParam String date) {
        return disponibiliteService.getDisponibilitesDisponiblesParDate(LocalDate.parse(date));
    }

    @PostMapping("/add-multiple")
    public List<Disponibilite> addMultipleDisponibilites(@RequestBody List<Disponibilite> disponibilites) {
        return disponibiliteService.saveAll(disponibilites);
    }

    // ✅ Supprimer
    @DeleteMapping("/{id}")
    public void supprimer(@PathVariable Long id) {
        disponibiliteService.supprimerDisponibilite(id);
    }
}
