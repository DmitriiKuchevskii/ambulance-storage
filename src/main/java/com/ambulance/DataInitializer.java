package com.ambulance;

import com.ambulance.domain.hibernate.entities.User;
import com.ambulance.domain.hibernate.repository.UserRepository;
import com.ambulance.security.Roles;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@Slf4j
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final String APPLICATION_DEFAULT_ADMIN_NAME = System.getenv("APPLICATION_DEFAULT_ADMIN_NAME");
    private static final String APPLICATION_DEFAULT_ADMIN_PASS = System.getenv("APPLICATION_DEFAULT_ADMIN_PASS");

    private final UserRepository users;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (APPLICATION_DEFAULT_ADMIN_NAME == null) {
            log.warn("APPLICATION_DEFAULT_ADMIN_NAME env variable is not provided. Assuming no need to create a new admin user.");
        } else {
            User adminUser = users.findByUsername(APPLICATION_DEFAULT_ADMIN_NAME).orElse(null);
            if (adminUser != null) {
                if (adminUser.getRoles().contains(Roles.ROLE_ADMIN)) {
                    log.info("Default admin name '{}' is present in system. No need to create a new one.", APPLICATION_DEFAULT_ADMIN_NAME);
                } else {
                    log.warn("User name '{}' is present in system but they do not have admin privileges. Assuming no need to change it.", APPLICATION_DEFAULT_ADMIN_NAME);
                }
            } else if (APPLICATION_DEFAULT_ADMIN_NAME.isEmpty()) {
                log.warn("APPLICATION_DEFAULT_ADMIN_NAME env variable is provided but it is empty. Admin user will not be created.");
            } else if (APPLICATION_DEFAULT_ADMIN_PASS == null) {
                log.warn("APPLICATION_DEFAULT_ADMIN_NAME was provided, but APPLICATION_DEFAULT_ADMIN_PASS wasn't. Admin user will not be created");
            } else if (APPLICATION_DEFAULT_ADMIN_PASS.isEmpty()) {
                log.warn("APPLICATION_DEFAULT_ADMIN_NAME was provided, but APPLICATION_DEFAULT_ADMIN_PASS is empty. Admin user will not be created");
            } else {
                log.info("Default admin name '{}' is not present in system. Creating a new one with default password", APPLICATION_DEFAULT_ADMIN_NAME);
                this.users.save(User.builder()
                        .username(APPLICATION_DEFAULT_ADMIN_NAME)
                        .password(this.passwordEncoder.encode(APPLICATION_DEFAULT_ADMIN_PASS))
                        .roles(Collections.singletonList(Roles.ROLE_ADMIN))
                        .build()
                );
            }
        }
    }
}
