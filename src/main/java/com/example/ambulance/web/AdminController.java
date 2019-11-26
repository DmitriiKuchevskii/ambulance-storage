package com.example.ambulance.web;

import com.example.ambulance.domain.Patient;
import com.example.ambulance.domain.User;
import com.example.ambulance.repository.PatientRepository;
import com.example.ambulance.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminController {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository users;

    private final PatientRepository patients;

    @PostMapping("/NewUser")
    @Transactional
    public ResponseEntity newUser(@RequestBody NewUserRequest newUser) {
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

    @GetMapping("/GetAll")
    public ResponseEntity all() {
        return ok(patients.findAll());
    }

    @GetMapping("/Get")
    public ResponseEntity one(@RequestHeader(value="id")  Long id) {
        return ok(patients.findById(id)
                .orElseThrow(() -> new PatientDoesNotExistException(id)));
    }

    @DeleteMapping("/Remove")
    @Transactional
    public ResponseEntity remove(@RequestHeader(value="id") Long id) {
        Patient patient = patients.findById(id)
                .orElseThrow(() -> new PatientDoesNotExistException(id));
        patients.delete(patient);
        return ok().build();
    }
}
