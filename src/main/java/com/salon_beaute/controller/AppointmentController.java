package com.salon_beaute.controller;

import com.salon_beaute.model.Appointment;
import com.salon_beaute.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // 🔹 Récupérer tous les rendez-vous
    @GetMapping("/all")
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    // 🔹 Récupérer tous les rendez-vous depuis aurjourd'hui
    @GetMapping("/all/from-today")
    public List<Appointment> getAllAppointmentsFromToday() { return appointmentService.getAllAppointmentsFromToday(); }

    // 🔹 Récupérer un rendez-vous par ID
    @GetMapping("/{id}")
    public Optional<Appointment> getAppointmentById(@PathVariable Long id) {
        return appointmentService.getAppointmentById(id);
    }

    // 🔹 Ajouter un rendez-vous
    @PostMapping("/add")
    public Appointment addAppointment(@RequestBody Appointment appointment) {
        return appointmentService.addAppointment(appointment);
    }

    // 🔹 Mettre à jour un rendez-vous
    @PutMapping("/update/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        return appointmentService.updateAppointment(id, appointment);
    }

    // 🔹 Mettre à jour le statut d'un rendez-vous
    @PatchMapping("/update/{id}/status")
    public Appointment updateAppointmentStatus(@PathVariable Long id, @RequestParam String status) {
        return appointmentService.updateAppointmentStatus(id, status);
    }

    // 🔹 Supprimer un rendez-vous
    @DeleteMapping("/delete/{id}")
    public void deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
    }

    // 🔹 Filtrer par statut
    @GetMapping("/status/{status}")
    public List<Appointment> getAppointmentsByStatus(@PathVariable String status) {
        return appointmentService.getAppointmentsByStatus(status);
    }

    // 🔹 Filtrer par employé
    @GetMapping("/employee/{employeeName}")
    public List<Appointment> getAppointmentsByEmployee(@PathVariable String employeeName) {
        return appointmentService.getAppointmentsByEmployee(employeeName);
    }

    // 🔹 Filtrer par plage de dates
    @GetMapping("/date-range")
    public List<Appointment> getAppointmentsByDateRange(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        return appointmentService.getAppointmentsByDateRange(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }

    // ✅ Récupérer les rendez-vous d'un client spécifique
    @GetMapping("/client/{clientName}")
    public List<Appointment> getAppointmentsByClient(@PathVariable String clientName) {
        return appointmentService.getAppointmentsByClient(clientName);
    }
}
