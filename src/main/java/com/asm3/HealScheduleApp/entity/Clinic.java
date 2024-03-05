package com.asm3.HealScheduleApp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clinic")
@Data
@NoArgsConstructor
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "price")
    private double price;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;


}
