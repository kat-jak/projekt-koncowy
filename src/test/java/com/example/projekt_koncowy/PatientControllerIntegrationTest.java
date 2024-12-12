package com.example.projekt_koncowy;

import com.example.projekt_koncowy.model.Appointment;
import com.example.projekt_koncowy.model.Patient;
import com.example.projekt_koncowy.repository.AppointmentRepository;
import com.example.projekt_koncowy.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PatientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private Patient testPatient;

    @BeforeEach
    void setUp() {
        appointmentRepository.deleteAll();
        patientRepository.deleteAll();

        // Tworzymy testowego pacjenta
        testPatient = new Patient();
        testPatient.setId(123L);
        testPatient.setFirstName("John");
        testPatient.setLastName("Doe");
        testPatient.setEmail("john.doe@example.com");
        patientRepository.save(testPatient);
    }

    @Test
    void testListAppointments() throws Exception {
        // Tworzymy przykładową wizytę dla pacjenta
        Appointment appointment = new Appointment();
        appointment.setDateTime(LocalDateTime.now());
        appointment.setPatient(testPatient);
        appointmentRepository.save(appointment);

        mockMvc.perform(get("/patients/" + testPatient.getId() + "/appointments"))
                .andExpect(status().isOk())
                .andExpect(view().name("appointments"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attributeExists("appointments"))
                .andExpect(model().attribute("appointments", hasSize(1)));
    }

    @Test
    void testNewAppointmentForm() throws Exception {
        mockMvc.perform(get("/patients/" + testPatient.getId() + "/appointments/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("new_appointment"))
                .andExpect(model().attributeExists("appointment"));
    }

    @Test
    void testSaveAppointment() throws Exception {
        mockMvc.perform(post("/patients/" + testPatient.getId() + "/appointments")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("dateTime", "2024-06-15T10:00:00"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/" + testPatient.getId() + "/appointments"));

        // Sprawdzamy, czy wizyta została zapisana
        mockMvc.perform(get("/patients/" + testPatient.getId() + "/appointments"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("appointments"))
                .andExpect(model().attribute("appointments", hasSize(1)));
    }

    @Test
    void testDeleteAppointment() throws Exception {
        // Tworzymy przykładową wizytę
        Appointment appointment = new Appointment();
        appointment.setDateTime(LocalDateTime.now());
        appointment.setPatient(testPatient);
        appointmentRepository.save(appointment);

        mockMvc.perform(get("/patients/" + testPatient.getId() + "/appointments/delete/" + appointment.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients/" + testPatient.getId() + "/appointments"));

        // Sprawdzamy, czy wizyta została usunięta
        mockMvc.perform(get("/patients/" + testPatient.getId() + "/appointments"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("appointments", hasSize(0)));
    }
}
