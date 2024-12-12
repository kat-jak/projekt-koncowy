package com.example.projekt_koncowy.controller;

import com.example.projekt_koncowy.model.Patient;
import com.example.projekt_koncowy.service.PatientService;

import com.example.projekt_koncowy.model.Appointment;
import com.example.projekt_koncowy.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/{id}/appointments")
    public String listAppointments(@PathVariable Long id, Model model) {
        Patient patient = patientService.getPatientById(id);
        model.addAttribute("patient", patient);
        model.addAttribute("appointments", appointmentService.getAppointmentsByPatientId(id));
        return "appointments";
    }

    @GetMapping("/{id}/appointments/new")
    public String newAppointmentForm(@PathVariable Long id, Model model) {
        Appointment appointment = new Appointment();
        appointment.setPatient(patientService.getPatientById(id));
        model.addAttribute("appointment", appointment);
        return "new_appointment";
    }

    @PostMapping("/{id}/appointments")
    public String saveAppointment(@PathVariable Long id, @ModelAttribute("appointment") Appointment appointment) {
        appointment.setPatient(patientService.getPatientById(id));
        appointmentService.saveAppointment(appointment);
        return "redirect:/patients/" + id + "/appointments";
    }

    @GetMapping("/{id}/appointments/delete/{appointmentId}")
    public String deleteAppointment(@PathVariable Long id, @PathVariable Long appointmentId) {
        appointmentService.deleteAppointment(appointmentId);
        return "redirect:/patients/" + id + "/appointments";
    }
}
