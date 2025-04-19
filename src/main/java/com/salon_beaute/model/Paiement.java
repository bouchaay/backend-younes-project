package com.salon_beaute.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "paiements")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long montant; // en centimes (ex: 1000 = 10$)

    @Column(nullable = false)
    private String clientEmail;

    @Column(nullable = false)
    private String clientSecret; // généré par Stripe

    @Column(nullable = false)
    private String statut; // "en_cours", "réussi", "echoue", etc.

    private String methode; // "card", "apple_pay", "google_pay", "paypal" (facultatif)

    @Column(nullable = false)
    private LocalDateTime datePaiement;

    @PrePersist
    public void onCreate() {
        this.datePaiement = LocalDateTime.now();
    }
}
