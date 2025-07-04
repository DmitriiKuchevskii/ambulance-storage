package com.ambulance.security.jwt;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private static final String JWT_TOKEN_NAME = "JWT";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        String token = Optional.ofNullable(WebUtils.getCookie(req, JWT_TOKEN_NAME))
                .map(Cookie::getValue)
                .orElse(req.getHeader(JWT_TOKEN_NAME));
        log.info("token = {}", token);
        if (token != null  && jwtTokenProvider.validateToken(token)) {
            SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));
        }
        chain.doFilter(req, res);
    }
}
