package com.example.demo.web;

import com.example.demo.domain.Patient;
import com.example.demo.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PatientController {

    private final PatientRepository patients;

    @GetMapping("/admin/GetAll")
    public ResponseEntity adminAll() {
        return ok(patients.findAll());
    }

    @GetMapping("/GetAll")
    public ResponseEntity all() {
        return ok(patients.findAllPatientInfoOnlyBy());
    }

    @GetMapping("/Get")
    public ResponseEntity one(@RequestHeader(value="id")  Long id) {
        return ok(patients.findOnePatientInfoOnlyById(id).orElseThrow(RuntimeException::new));
    }

    @GetMapping("/admin/Get")
    public ResponseEntity adminOne(@RequestHeader(value="id")  Long id) {
        return ok(patients.findById(id).orElseThrow(RuntimeException::new));
    }

    @PostMapping("/Add")
    public ResponseEntity add(@RequestBody PatientForm form) {
        Patient patient = Patient.builder()
                .name(form.getName())
                .surname(form.getSurname())
                .patronymic(form.getPatronymic())
                .address(form.getAddress())
                .passportSeries(form.getPassportSeries())
                .passportNumber(form.getPassportNumber())
                .medicalInsuranceNumber(form.getMedicalInsuranceNumber())
                .dob(form.getDob())
                .data(form.getData())
                .build();
        patients.save(patient);
        return ok(Map.of("id", patient.getId()));
    }

    @DeleteMapping("/admin/Remove")
    public ResponseEntity remove(@RequestHeader(value="id") Long id) {
        Patient patient = patients.findById(id).orElseThrow(RuntimeException::new);
        patients.delete(patient);
        return ok().build();
    }

}


