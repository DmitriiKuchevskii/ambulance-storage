package com.example.ambulance.web;

import com.example.ambulance.domain.Patient;
import com.example.ambulance.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Transactional
public class PatientController {

    private final PatientRepository patients;

    @GetMapping("/GetAll")
    public ResponseEntity all() {
        return ok(patients.findAllPatientInfoOnlyBy());
    }

    @GetMapping("/Get")
    public ResponseEntity one(@RequestHeader(value="id")  Long id) {
        return ok(patients.findOnePatientInfoOnlyById(id)
                    .orElseThrow(() -> new PatientDoesNotExistException(id)));
    }

    @PostMapping("/Add")
    public ResponseEntity add(@RequestBody PatientForm form) {
        Patient patient = Patient.builder()
                .fullName(form.getFullName())
                .address(form.getAddress())
                .age(form.getAge())
                .code(form.getCode())
                .data(form.getData())
                .homeless(form.getHomeless())
                .regularPatient(form.getRegularPatient())
                .result(form.getResult())
                .sex(form.getSex())
                .date(form.getDate())
                .team(form.getTeam())
                .build();
        patients.save(patient);
        return ok(Map.of("id", patient.getId()));
    }
}


