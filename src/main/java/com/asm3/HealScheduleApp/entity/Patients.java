package com.asm3.HealScheduleApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
@Data
@NoArgsConstructor
public class Patients {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
//    @JsonIgnoreProperties({"password","matchingPassword"})
    private User user;

    @OneToOne
    @JoinColumn(name = "doctor_information_id")
    private DoctorInformation doctorInformation;

    @Column(name = "pathology_name")
    private String pathologyName;

    @OneToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
