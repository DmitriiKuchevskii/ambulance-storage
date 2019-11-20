package com.example.ambulance.web;

import com.example.ambulance.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
public class UserAlreadyExistsException extends RuntimeException {
    private final User newUser;
}
