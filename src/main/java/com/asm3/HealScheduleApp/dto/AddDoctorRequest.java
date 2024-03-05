package com.asm3.HealScheduleApp.dto;

import com.asm3.HealScheduleApp.entity.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddDoctorRequest {

    @NotNull(message = "is required")
    private String introduce;

    @NotNull(message = "is required")
    private String trainingProcess;

    @NotNull(message = "is required")
    private String achievements;

    private long specializationId;

    @Valid
    private User user;


}
