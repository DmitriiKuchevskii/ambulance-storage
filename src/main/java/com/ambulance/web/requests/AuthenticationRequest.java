package com.ambulance.web.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class AuthenticationRequest implements Serializable {
    private static final long serialVersionUID = -6986746375915710855L;

    @NotEmpty
    private final String username;

    @NotEmpty
    private final String password;
}
