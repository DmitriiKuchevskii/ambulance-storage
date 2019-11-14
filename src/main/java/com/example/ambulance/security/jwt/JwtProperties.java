package com.example.ambulance.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {

	private final String secretKey =
			"52lkCehoGYo957LDZpKXBd4FH5ZBeSfyVKMqJxVsrw2shTVoo0iiWMWbZ81EzuKvEOwN2n6NODq1Y8tUE027" +
			"Jqydd6_8LJgs9XCtI58mCm-uO5RsorD39ufcRgkpRo1tCFh5TWIbXKqoCGj3pDq7u2rtRLIKZ58e3xuoU-pu" +
			"nM2z_XCsdjo8KCs0w9kHG9Un13A3O_UTwcVGO3-kM65S8Ne7gO34Ql_btFUcdklFX7EfyshMrx4fLNL8KGhC" +
			"3XIPrmRyc6wQ_dxqVPfE69N-e3kNOiaq0iSy5f_n5GzileZb-ZwvcIQFD9WqsHhfvzsXGaHYubOXE2gOzS6e" +
			"hH_xWzFq6jXcpts59W7rRseqdJYV2CmNV_cUmvs7bPDZptlXj42GVOnrTzrR2F74IcxbzxBoonxtvNtZHbaK" +
			"KKbWZHfzDv2GtR6oCE6CxYPbMcAHnztMwpBH_xOBH4fah9gHrBY4FXx5F4p7d5yoi8MbaD9IQbs7tbMHBZZe" +
			"7c1WRPKrH_JXohssq_oTjoBtVC8qRh0EFUfWd75VLozrlyiEg_I7ChLVJh4tZa_D7NNvfK4DBqqft3_HyKc9" +
			"IBHvzpRfTX2AiNR_iDZa3rFOdf41rDeFwQOSI_abL_3qjv_l_mw7AoJy2sfnPUzxApAtaqDljbn5G5oEg0TqKBASmHWYV2";

	//validity in milliseconds
	private long validityInMs = 3600000; // 1h
}
