package com.example.projekt_koncowy.repository;

import com.example.projekt_koncowy.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
