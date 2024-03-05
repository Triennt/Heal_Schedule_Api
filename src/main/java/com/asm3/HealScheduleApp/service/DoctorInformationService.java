package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.dto.AddDoctorRequest;
import com.asm3.HealScheduleApp.dto.GeneralSearchRequest;
import com.asm3.HealScheduleApp.entity.DoctorInformation;

import java.util.List;

public interface DoctorInformationService {
    List<DoctorInformation> generalSearch(GeneralSearchRequest generalSearchRequest);

    List<DoctorInformation> specializationSearch(String specialization);

    DoctorInformation findById(long id);

    DoctorInformation findByEmail(String email);

    DoctorInformation save(AddDoctorRequest doctor);

    DoctorInformation getDoctorSession();
}
