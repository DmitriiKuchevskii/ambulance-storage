package com.ambulance.web.exceptionhandlers;

//import com.example.demo.com.ambulance.security.jwt.InvalidJwtAuthenticationException;
import com.ambulance.web.exceptions.PatientDoesNotExistException;
import com.ambulance.web.exceptions.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import static org.springframework.http.ResponseEntity.status;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler {
    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ResponseEntity<?> userAlreadyExists(UserAlreadyExistsException ex, WebRequest request) {
        log.error("Can not create a new user '{}' because it is already exists.", ex.getNewUser().getUsername());
        return status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(value = {PatientDoesNotExistException.class})
    public ResponseEntity<?> patientDoesNotExist(PatientDoesNotExistException ex, WebRequest request) {
        log.error("Patient with ID '{}' does not exist.", ex.getId());
        return status(HttpStatus.NOT_FOUND).build();
    }
}
