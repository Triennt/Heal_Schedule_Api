package com.asm3.HealScheduleApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    @Column(name = "full_name")
    @NotNull(message = "is required")
    private String fullName;

    @Column(name = "gender")
    @NotNull(message = "is required")
    private String gender;

    @Column(name = "phone")
    @Pattern(regexp = "0[0-9]{9}", message = "invalid phone number.")
    private String phone;

    @Column(name = "birthday")
    @NotNull(message = "is required")
    private LocalDate birthday;

    @Column(name = "address")
    @NotNull(message = "is required")
    private String address;

    @Column(name = "pathology")
    @NotNull(message = "is required")
    private String pathologyName;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"password","matchingPassword","sessionToken"})
    private User user;

    @OneToOne
    @JoinColumn(name = "doctor_information_id")
    private DoctorInformation doctorInformation;

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
