package com.example.projekt_koncowy;

import com.example.projekt_koncowy.model.Appointment;
import com.example.projekt_koncowy.repository.AppointmentRepository;
import com.example.projekt_koncowy.service.AppointmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentService appointmentService;

    private Appointment sampleAppointment;

    @BeforeEach
    void setUp() {
        sampleAppointment = new Appointment();
        sampleAppointment.setId(1L);
        sampleAppointment.setId(1L);
        sampleAppointment.setDateTime(LocalDateTime.parse("2024-06-15T10:00:00"));
    }

    @Test
    void testGetAppointmentsByPatientId() {
        // Given
        Long patientId = 1L;
        List<Appointment> appointments = Arrays.asList(sampleAppointment);
        when(appointmentRepository.findByPatientId(patientId)).thenReturn(appointments);

        // When
        List<Appointment> result = appointmentService.getAppointmentsByPatientId(patientId);

        // Then
        assertEquals(1, result.size());
        assertEquals(sampleAppointment, result.get(0));
        verify(appointmentRepository, times(1)).findByPatientId(patientId);
    }

    @Test
    void testSaveAppointment() {
        // Given
        when(appointmentRepository.save(sampleAppointment)).thenReturn(sampleAppointment);

        // When
        Appointment result = appointmentService.saveAppointment(sampleAppointment);

        // Then
        assertNotNull(result);
        assertEquals(sampleAppointment, result);
        verify(appointmentRepository, times(1)).save(sampleAppointment);
    }

    @Test
    void testDeleteAppointment() {
        // Given
        Long appointmentId = 1L;
        doNothing().when(appointmentRepository).deleteById(appointmentId);

        // When
        appointmentService.deleteAppointment(appointmentId);

        // Then
        verify(appointmentRepository, times(1)).deleteById(appointmentId);
    }
}
