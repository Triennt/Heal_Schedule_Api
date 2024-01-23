package com.asm3.HealScheduleApp.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GeneralSearchRequest {
    private String area = "";
    private String pathology = "";
    private double price = Double.MAX_VALUE;
    private String clinic = "";
}
