package com.asm3.HealScheduleApp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Table(name = "specialization")
@Data
@NoArgsConstructor
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @OneToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;
}
