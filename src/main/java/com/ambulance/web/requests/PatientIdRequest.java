package com.ambulance.web.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class PatientIdRequest {
    @NotNull
    private final Long patientId;
}
