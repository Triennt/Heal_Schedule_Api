package com.asm3.HealScheduleApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doctor_information")
@Data
@NoArgsConstructor
public class DoctorInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "introduce")
    @NotNull(message = "is required")
    private String introduce;

    @Column(name = "training_process")
    @NotNull(message = "is required")
    private String trainingProcess;

    @Column(name = "achievements")
    @NotNull(message = "is required")
    private String achievements;

    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private Specialization specialization;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @Valid
    @JsonIgnoreProperties({"password","matchingPassword","sessionToken"})
    private User user;
}
