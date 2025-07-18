package com.ambulance.web.controllers;

import com.ambulance.domain.hibernate.entities.Patient;
import com.ambulance.domain.hibernate.entities.User;
import com.ambulance.domain.hibernate.repository.PatientRepository;
import com.ambulance.domain.hibernate.repository.UserRepository;
import com.ambulance.web.AmbulanceApi;
import com.ambulance.web.exceptions.PatientDoesNotExistException;
import com.ambulance.web.requests.NewPatientRequest;
import com.ambulance.web.requests.PatientIdRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping(AmbulanceApi.API_USER_ROOT_REQUEST_MAP)
@AllArgsConstructor
public class PatientController {

    private final PatientRepository patients;

    private final UserRepository users;

    @GetMapping(AmbulanceApi.API_GET_ALL_PATIENTS)
    public ResponseEntity<?> all() {
        return ok(patients.findAllPatientInfoOnlyBy());
    }

    @GetMapping(AmbulanceApi.API_GET_ALL_CURRENT_USER_PATIENTS)
    public ResponseEntity<?> getAllCurrentUserPatients() {
        String curUserName = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User curUser = users.findByUsername(curUserName)
                .orElseThrow(() -> new UsernameNotFoundException(curUserName));

        return ok(patients.findAllPatientInfoOnlyByCreatedBy(curUser));
    }

    @PostMapping(AmbulanceApi.API_GET_PATIENT)
    public ResponseEntity<?> one(@RequestBody @Valid PatientIdRequest form) {
        return ok(patients.findOnePatientInfoOnlyById(form.getPatientId())
                .orElseThrow(() -> new PatientDoesNotExistException(form.getPatientId())));
    }

    @PostMapping(AmbulanceApi.API_ADD_PATIENT)
    @Transactional
    public ResponseEntity<?> add(@RequestBody @Valid NewPatientRequest form) {
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


