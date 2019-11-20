package com.example.ambulance.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class FilterErrorHandler extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        try {
            chain.doFilter(req, res);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException exc) {
            log.info("Access denied because of incorrect token format: {}", exc.toString());
            res.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid authorisation format");
        } catch (SignatureException | ExpiredJwtException exc) {
            log.info("Access denied because of invalid toke: {}", exc.toString());
            res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied");
        }
    }
}
