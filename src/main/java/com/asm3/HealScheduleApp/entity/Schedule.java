package com.asm3.HealScheduleApp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "schedule")
@Data
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @OneToOne
    @JoinColumn(name = "doctor_information_id")
    private DoctorInformation doctorInformation;

    @OneToOne
    @JoinColumn(name = "patients_id")
    private Patients patients;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private Time time;

    @Column(name = "price")
    private double price;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id")
    private Status status = new Status();

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
