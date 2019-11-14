package com.example.ambulance.web;

import com.example.ambulance.domain.Sex;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientForm {
    Date date;

    String team;

    String code;

    String result;

    Sex sex;

    Long age;

    String fullName;

    String address;

    Boolean regularPatient;

    Boolean homeless;

    String data;
}
