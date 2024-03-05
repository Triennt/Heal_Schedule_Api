package com.asm3.HealScheduleApp.dto;

import com.asm3.HealScheduleApp.entity.Patients;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BookRequest {

    @NotNull(message = "is required")
    private Long idDoctor;

    @NotNull(message = "is required")
    private LocalDate date;

    @NotNull(message = "is required")
    private Time time;

    @NotNull(message = "is required")
    private Double price;

    @Valid
    private Patients patients;

}
