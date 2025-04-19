package com.salon_beaute.repository;

import com.salon_beaute.model.Disponibilite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface DisponibiliteRepository extends JpaRepository<Disponibilite, Long> {
    List<Disponibilite> findByEmployeId(Long employeId);

    List<Disponibilite> findByDate(LocalDate date);

    List<Disponibilite> findByDateAndDisponibleTrue(LocalDate date);

    boolean existsByEmployeIdAndDateAndHeureDebut(Long employeId, LocalDate date, LocalTime heureDebut);

    boolean existsByEmployeIdAndDateAndHeureDebutAndHeureFin(Long employeId, LocalDate date, LocalTime heureDebut, LocalTime heureFin);

    Optional<Disponibilite> findByEmployeIdAndDateAndHeureDebutAndHeureFin(
            Long employeId,
            LocalDate date,
            LocalTime heureDebut,
            LocalTime heureFin
    );

}
