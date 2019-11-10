package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "patients")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Patient extends AbstractAuditableEntity<User, Long> implements Serializable {

    @Column
    @NotEmpty
    private String name;

    @Column
    @NotEmpty
    private String surname;

    @Column
    private String patronymic;

    @Column
    @NotEmpty
    private String address;

    @Column
    @NotNull
    private long passportSeries;

    @Column
    @NotNull
    private long passportNumber;

    @Column
    @NotNull
    private long medicalInsuranceNumber;

    @Column
    @NotNull
    private Date dob;

    @Column
    private String data;
}
