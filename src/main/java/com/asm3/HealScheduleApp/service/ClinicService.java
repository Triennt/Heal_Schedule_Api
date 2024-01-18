package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.entity.Clinic;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClinicService {
    List<Clinic> getTopClinic();
}
