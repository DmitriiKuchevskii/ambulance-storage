package com.ambulance.web.requests;

import com.ambulance.domain.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class NewPatientRequest implements Serializable {
    private static final long serialVersionUID = -7280379800686242875L;

    @NotNull
    private final Date date;

    @NotEmpty
    private final String team;

    @NotEmpty
    private final String code;

    @NotEmpty
    private final String result;

    @NotNull
    private final Sex sex;

    @NotNull
    private final Long age;

    @NotEmpty
    private final String fullName;

    @NotEmpty
    private final String address;

    @NotNull
    private final Boolean regularPatient;

    @NotNull
    private final Boolean homeless;

    @NotEmpty
    private final String data;
}
