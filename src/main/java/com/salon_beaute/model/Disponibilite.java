package com.salon_beaute.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "disponibilités")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Disponibilite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeId;

    private LocalDate date;

    private LocalTime heureDebut;
    private LocalTime heureFin;

    private boolean disponible; // Pour gérer les congés (actif/inactif)
    private boolean booked;
}
