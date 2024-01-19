package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.body.GeneralSearchRequest;
import com.asm3.HealScheduleApp.entity.DoctorInformation;

import java.util.List;

public interface DoctorInformationService {
    List<DoctorInformation> generalSearch(GeneralSearchRequest generalSearchRequest);

    List<DoctorInformation> specializationSearch(String specialization);
}