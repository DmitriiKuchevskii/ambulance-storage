package com.example.demo.repository;

import java.util.Date;

public interface PatientInfoOnly
{
    Long getId();

    String getName();

    String getSurname();

    String getPatronymic();

    String getAddress();

    long getPassportSeries();

    long getPassportNumber();

    long getMedicalInsuranceNumber();

    Date getDob();

    String getData();
}
