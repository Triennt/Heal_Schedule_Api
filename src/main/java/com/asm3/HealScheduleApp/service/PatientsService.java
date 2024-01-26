package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.entity.DoctorInformation;
import com.asm3.HealScheduleApp.entity.Patients;

import java.util.List;

public interface PatientsService {
    List<Patients> getPatients(DoctorInformation doctor);
    Patients findById(long id);
}
