package com.ambulance.domain.hibernate.entities;

import com.ambulance.domain.Sex;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "YYYY-MM-DD:HH:mm")
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
