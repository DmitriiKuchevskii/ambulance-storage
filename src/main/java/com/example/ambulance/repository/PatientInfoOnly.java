package com.example.ambulance.repository;

import com.example.ambulance.domain.Sex;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
