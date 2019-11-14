package com.example.ambulance.web;

//import com.example.demo.security.jwt.InvalidJwtAuthenticationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {

//    @ExceptionHandler(value = {VehicleNotFoundException.class})
//    public ResponseEntity vehicleNotFound(VehicleNotFoundException ex, WebRequest request) {
//        log.debug("handling VehicleNotFoundException...");
//        return notFound().build();
//    }
//
//    @ExceptionHandler(value = {InvalidJwtAuthenticationException.class})
//    public ResponseEntity invalidJwtAuthentication(InvalidJwtAuthenticationException ex, WebRequest request) {
//        log.debug("handling InvalidJwtAuthenticationException...");
//        return status(UNAUTHORIZED).build();
//    }
}
