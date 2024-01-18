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

    @Column(name = "pathology_name")
    private String pathologyName;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "time")
    private Time time;

    @Column(name = "price")
    private double price;

    @OneToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public DoctorInformation getDoctorInformation() {
        return doctorInformation;
    }

    public void setDoctorInformation(DoctorInformation doctorInformation) {
        this.doctorInformation = doctorInformation;
    }

    public Patients getPatients() {
        return patients;
    }

    public void setPatients(Patients patients) {
        this.patients = patients;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
