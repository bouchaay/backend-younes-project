package com.salon_beaute.service;

import com.salon_beaute.model.Appointment;
import com.salon_beaute.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    // 🔹 Récupérer tous les rendez-vous
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // Récupérer tous les rendez vous depuis aujourd'hui
    public List<Appointment> getAllAppointmentsFromToday() {
        LocalDate today = LocalDate.now();
        return appointmentRepository.findByDateGreaterThanEqual(today);
    }


    // 🔹 Récupérer un rendez-vous par ID
    public Optional<Appointment> getAppointmentById(Long id) {
        return appointmentRepository.findById(id);
    }

    // 🔹 Ajouter un rendez-vous
    public Appointment addAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    // 🔹 Mettre à jour un rendez-vous
    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        return appointmentRepository.findById(id)
                .map(existing -> {
                    existing.setClientName(updatedAppointment.getClientName());
                    existing.setEmployeeName(updatedAppointment.getEmployeeName());
                    existing.setService(updatedAppointment.getService());
                    existing.setDate(updatedAppointment.getDate());
                    existing.setDateCreation(updatedAppointment.getDateCreation());
                    existing.setDateAnnulation(updatedAppointment.getDateAnnulation());
                    existing.setDateTerminaison(updatedAppointment.getDateTerminaison());
                    existing.setTime(updatedAppointment.getTime());
                    existing.setStatus(updatedAppointment.getStatus());
                    return appointmentRepository.save(existing);
                }).orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé !"));
    }

    // 🔹 Mettre à jour le statut d'un rendez-vous
    public Appointment updateAppointmentStatus(Long id, String status) {
        return appointmentRepository.findById(id)
                .map(existing -> {
                    existing.setStatus(status);
                    return appointmentRepository.save(existing);
                }).orElseThrow(() -> new RuntimeException("Rendez-vous non trouvé !"));
    }

    // 🔹 Supprimer un rendez-vous
    public void deleteAppointment(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new RuntimeException("Rendez-vous non trouvé !");
        }
        appointmentRepository.deleteById(id);
    }

    // 🔹 Filtrer par statut
    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findByStatus(status);
    }

    // 🔹 Filtrer par employé
    public List<Appointment> getAppointmentsByEmployee(String employeeName) {
        return appointmentRepository.findByEmployeeName(employeeName);
    }

    // 🔹 Filtrer par plage de dates
    public List<Appointment> getAppointmentsByDateRange(LocalDate startDate, LocalDate endDate) {
        return appointmentRepository.findByDateBetween(startDate, endDate);
    }

    public List<Appointment> getAppointmentsByClient(String clientName) {
        return appointmentRepository.findByClientName(clientName);
    }

}
