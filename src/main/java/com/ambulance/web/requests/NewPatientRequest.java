package com.ambulance.web.requests;

import com.ambulance.domain.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.jackson.JsonComponent;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonComponent
public class NewPatientRequest {
    @NotNull
    private Date date;

    @NotEmpty
    private String team;

    @NotEmpty
    private String code;

    @NotEmpty
    private String result;

    @NotNull
    private Sex sex;

    @NotNull
    private Long age;

    @NotEmpty
    private String fullName;

    @NotEmpty
    private String address;

    @NotNull
    private Boolean regularPatient;

    @NotNull
    private Boolean homeless;

    @NotEmpty
    private String data;
}
