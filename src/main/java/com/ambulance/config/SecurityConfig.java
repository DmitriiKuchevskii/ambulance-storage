package com.ambulance.config;

import com.ambulance.domain.hibernate.repository.UserRepository;
import com.ambulance.security.AmbulanceUserDetailsService;
import com.ambulance.security.Roles;
import com.ambulance.security.jwt.JwtFilterExceptionHandler;
import com.ambulance.security.jwt.JwtTokenAuthenticationFilter;
import com.ambulance.security.jwt.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletResponse;

import static com.ambulance.security.Roles.ROLE_ADMIN;
import static com.ambulance.security.Roles.ROLE_USER;
import static com.ambulance.web.AmbulanceApiAccessPatterns.*;
import static com.ambulance.web.WebUiAccessPatterns.*;
import static org.apache.commons.lang3.ArrayUtils.addAll;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository users;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new AmbulanceUserDetailsService(users);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    public SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
        DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
        defaultWebSecurityExpressionHandler.setRoleHierarchy(Roles.ROLES_HIERARCHY);
        return defaultWebSecurityExpressionHandler;
    }

    @Bean
    public InternalResourceViewResolver internalResourceViewResolver() {
        return new InternalResourceViewResolver("/", ".html");
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
            .csrf().disable().cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .expressionHandler(webExpressionHandler())
                .antMatchers(addAll(WEB_UI_PUBLICLY_AVAILABLE, API_PUBLICLY_AVAILABLE)).permitAll()
                .antMatchers(addAll(WEB_UI_ADMIN_ONLY_AVAILABLE, API_ADMIN_ONLY_AVAILABLE)).hasAuthority(ROLE_ADMIN)
                .antMatchers(addAll(WEB_UI_USER_ONLY_AVAILABLE, API_USER_ONLY_AVAILABLE)).hasAuthority(ROLE_USER)
                .anyRequest().denyAll()
            .and()
                .addFilterBefore(new JwtFilterExceptionHandler(), LogoutFilter.class)
                .addFilterBefore(new JwtTokenAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
                .authenticationEntryPoint((req, res, exc) -> res.setStatus(HttpServletResponse.SC_UNAUTHORIZED))
                .accessDeniedHandler((req, res, exc) -> res.setStatus(HttpServletResponse.SC_NO_CONTENT));
    }
}

