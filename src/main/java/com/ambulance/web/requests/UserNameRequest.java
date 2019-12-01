package com.ambulance.web.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
public class UserNameRequest {
    @NotEmpty
    private final String userName;
}
