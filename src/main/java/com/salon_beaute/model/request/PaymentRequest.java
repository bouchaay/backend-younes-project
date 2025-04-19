package com.salon_beaute.model.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PaymentRequest {
    private Long amount; // en centimes, ex: 2000 = 20$
    private String clientName;
    private String clientEmail;
    private String appointmentId; // Optionnel si tu veux lier à un rendez-vous
}
