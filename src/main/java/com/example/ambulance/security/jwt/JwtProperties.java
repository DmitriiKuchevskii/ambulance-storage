package com.example.ambulance.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {

	private final String secretKey = System.getenv("APPLICATION_JWT_SECRET_KEY");

	//validity in milliseconds
	private long validityInMs = 3600000; // 1h
}
