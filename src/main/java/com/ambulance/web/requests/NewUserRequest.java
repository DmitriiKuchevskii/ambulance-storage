package com.ambulance.web.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class NewUserRequest{

    @NotEmpty
    private final String username;

    @NotEmpty
    private final String password;

    @NotEmpty
    private final List<String> roles;
}
