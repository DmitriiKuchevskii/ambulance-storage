package com.example.ambulance.security.jwt;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class JwtProperties {
    private final String secretKey = System.getenv("APPLICATION_JWT_SECRET_KEY");

    private final long validity = Long.parseLong(System.getenv("APPLICATION_JWT_SECRET_KEY_VALIDITY"));
}
