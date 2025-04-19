package com.salon_beaute.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "rendez-vous")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="client_name")
    private String clientName;
    @Column(name="client_email")
    private String clientEmail;
    @Column(name="employee_name")
    private String employeeName;
    @Column(name="service_name", nullable = false)
    private String service;
    @Column(name="date_rdv", nullable = false)
    private LocalDate date;
    @Column(name="heure_rdv", nullable = false)
    private LocalTime time;
    @Column(name="date_creation", nullable = false)
    private LocalDate dateCreation;
    @Column(name="date_annulation")
    private LocalDate dateAnnulation;
    @Column(name="date_terminaison")
    private LocalDate dateTerminaison;
    @Column(name="status", nullable = false)
    private String status;
}
