package com.ambulance.web.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class PatientIdRequest implements Serializable {
    private static final long serialVersionUID = 2024058317466605317L;

    @NotNull
    private final Long patientId;
}
