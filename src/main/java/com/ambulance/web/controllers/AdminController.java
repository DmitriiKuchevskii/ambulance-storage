package com.ambulance.web.controllers;

import com.ambulance.domain.hibernate.entities.Patient;
import com.ambulance.domain.hibernate.entities.User;
import com.ambulance.domain.hibernate.repository.PatientRepository;
import com.ambulance.domain.hibernate.repository.UserRepository;
import com.ambulance.web.AmbulanceApi;
import com.ambulance.web.requests.UserNameRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.ambulance.web.exceptions.PatientDoesNotExistException;
import com.ambulance.web.exceptions.UserAlreadyExistsException;
import com.ambulance.web.requests.NewUserRequest;
import com.ambulance.web.requests.PatientIdRequest;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping(AmbulanceApi.API_ADMIN_ROOT_REQUEST_MAP)
@AllArgsConstructor
public class AdminController {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository users;

    private final PatientRepository patients;

    @PostMapping(AmbulanceApi.API_ADD_NEW_USER)
    @Transactional
    public ResponseEntity<?> addNewUser(@RequestBody @Valid NewUserRequest newUser) {
        users.findByUsername(newUser.getUsername())
                .ifPresent(user -> { throw new UserAlreadyExistsException(user); });

        users.save(User.builder()
                .username(newUser.getUsername())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .roles(newUser.getRoles())
                .build()
        );
        return ok(Map.of("username", newUser.getUsername(), "roles", newUser.getRoles()));
    }

    @DeleteMapping(AmbulanceApi.API_REMOVE_USER)
    @Transactional
    public ResponseEntity<?> removeUser(@RequestBody @Valid UserNameRequest form) {
        User user = users.findByUsername(form.getUserName())
                .orElseThrow(() -> new UsernameNotFoundException(form.getUserName()));
        users.delete(user);
        return ok().build();
    }

    @GetMapping(AmbulanceApi.API_GET_PATIENT)
    public ResponseEntity<?> getPatient(@RequestBody @Valid PatientIdRequest form) {
        return ok(patients.findById(form.getPatientId())
                .orElseThrow(() -> new PatientDoesNotExistException(form.getPatientId())));
    }

    @GetMapping(AmbulanceApi.API_GET_ALL_PATIENTS)
    public ResponseEntity<?> getAllPatients() {
        return ok(patients.findAll());
    }

    @DeleteMapping(AmbulanceApi.API_REMOVE_PATIENT)
    @Transactional
    public ResponseEntity<?> removePatient(@RequestBody @Valid PatientIdRequest form) {
        Patient patient = patients.findById(form.getPatientId())
                .orElseThrow(() -> new PatientDoesNotExistException(form.getPatientId()));
        patients.delete(patient);
        return ok().build();
    }
}
