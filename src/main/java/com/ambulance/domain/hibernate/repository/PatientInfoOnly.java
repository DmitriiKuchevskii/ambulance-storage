package com.ambulance.domain.hibernate.repository;

import com.ambulance.domain.Sex;

import java.util.Date;

public interface PatientInfoOnly
{
    Long getId();

    Date getDate();

    String getTeam();

    String getCode();

    String getResult();

    Sex getSex();

    Long getAge();

    String getFullName();

    String getAddress();

    Boolean getRegularPatient();

    Boolean getHomeless();

    String getData();
}
