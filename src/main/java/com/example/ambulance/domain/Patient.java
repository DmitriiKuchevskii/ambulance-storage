package com.example.ambulance.domain;

import lombok.*;
import org.hibernate.validator.constraints.Range;

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
    @NotNull
    private Date date;

    @Column
    @NotEmpty
    private String team;

    @Column
    @NotEmpty
    private String code;

    @Column
    @NotEmpty
    private String result;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull
    private Sex sex;

    @Column
    @NotNull
    @Range(min = 0, max = 150)
    private Long age;

    @Column
    @NotEmpty
    private String fullName;

    @Column
    @NotEmpty
    private String address;

    @Column
    @NotNull
    private Boolean regularPatient;

    @Column
    @NotNull
    private Boolean homeless;

    @Column
    @NotEmpty
    private String data;
}
