package com.example.ambulance.repository;

import com.example.ambulance.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "patients", collectionResourceRel = "patients", itemResourceRel = "patient")
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<PatientInfoOnly> findOnePatientInfoOnlyById(Long id);
    List<PatientInfoOnly> findAllPatientInfoOnlyBy();
}
