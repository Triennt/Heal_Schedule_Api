package com.asm3.HealScheduleApp.dto;

import com.asm3.HealScheduleApp.entity.Specialization;
import com.asm3.HealScheduleApp.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
//    @JsonIgnoreProperties({"password","matchingPassword"})
    private User user;


}
