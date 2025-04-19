package com.salon_beaute.repository;

import com.salon_beaute.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByStatus(String status);
    List<Appointment> findByEmployeeName(String employeeName);
    List<Appointment> findByDateBetween(LocalDate startDate, LocalDate endDate);
    List<Appointment> findByClientName(String clientName);
    int countByClientEmailAndStatus(String clientEmail, String status);
    int countByEmployeeNameAndStatus(String employeeName, String status);
    List<Appointment> findByDateGreaterThanEqual(LocalDate date);
}
