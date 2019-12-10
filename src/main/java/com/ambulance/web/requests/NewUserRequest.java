package com.ambulance.web.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class NewUserRequest implements Serializable {

    private static final long serialVersionUID = 6198221426165031927L;

    @NotEmpty
    private final String username;

    @NotEmpty
    private final String password;

    @NotEmpty
    private final List<String> roles;
}
