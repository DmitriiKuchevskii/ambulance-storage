package com.ambulance.web.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class PatientDoesNotExistException extends RuntimeException {
    private final Long id;
}
