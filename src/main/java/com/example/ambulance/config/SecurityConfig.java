package com.example.ambulance.config;

import com.example.ambulance.security.jwt.JwtAuthenticationEntryPoint;
import com.example.ambulance.security.jwt.JwtSecurityConfigurer;
import com.example.ambulance.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
                .headers()
                    .contentTypeOptions()
                .and()
                    .xssProtection()
                .and()
                    .cacheControl()
                .and()
                    .httpStrictTransportSecurity()
                .and()
                    .frameOptions()
                .and()
                    .contentSecurityPolicy("script-src 'self'")
                .and().and()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/api/admin/*").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and().exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
            .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }


}

