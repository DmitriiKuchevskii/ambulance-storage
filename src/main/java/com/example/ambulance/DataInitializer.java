package com.example.ambulance;

import com.example.ambulance.domain.User;
import com.example.ambulance.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
