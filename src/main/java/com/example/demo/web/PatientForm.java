package com.example.demo.web;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientForm {
    private String name;

    private String surname;

    private String patronymic;

    private String address;

    private long passportSeries;

    private long passportNumber;

    private long medicalInsuranceNumber;

    private Date dob;

    private String data;
}
