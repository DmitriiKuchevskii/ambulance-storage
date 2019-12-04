package com.ambulance.web.exceptions;

import com.ambulance.domain.hibernate.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class UserAlreadyExistsException extends RuntimeException {
    private final User newUser;
}
