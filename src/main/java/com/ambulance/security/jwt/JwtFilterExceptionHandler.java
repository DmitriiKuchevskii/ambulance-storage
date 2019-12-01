package com.ambulance.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtFilterExceptionHandler extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(req, res);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException exc) {
            log.info("Access denied because of incorrect token format: {}", exc.toString());
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (SignatureException | ExpiredJwtException exc) {
            log.info("Access denied because of invalid token: {}", exc.toString());
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
