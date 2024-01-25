package com.asm3.HealScheduleApp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "active")
@Data
@NoArgsConstructor
public class ActiveStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "value")
    private boolean active;
}
