package com.example.demo;

import com.example.demo.domain.Patient;
import com.example.demo.domain.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.PatientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository users;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (users.findByUsername("admin").isPresent())
            return;

        this.users.save(User.builder()
            .username("admin")
            .password(this.passwordEncoder.encode("h_jc2qLD0ky0RsrhlSvgvT845Zk"))
            .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
            .build()
        );
    }
}
