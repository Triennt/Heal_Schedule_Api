package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.body.GeneralSearchRequest;
import com.asm3.HealScheduleApp.dao.DoctorInformationRepository;
import com.asm3.HealScheduleApp.entity.DoctorInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorInformationServiceImpl implements DoctorInformationService{
    @Autowired
    private DoctorInformationRepository doctorInformationRepository;
    @Override
    public List<DoctorInformation> generalSearch(GeneralSearchRequest generalSearchRequest) {
        String area = generalSearchRequest.getArea();
        String pathology = generalSearchRequest.getPathology();
        double price = generalSearchRequest.getPrice();
        String clinic = generalSearchRequest.getClinic();

        return doctorInformationRepository.generalSearch(area,pathology,price,clinic);
    }

    @Override
    public List<DoctorInformation> specializationSearch(String specialization) {
        return doctorInformationRepository.specializationSearch(specialization);
    }

    @Override
    public DoctorInformation findById(long id) {
        return doctorInformationRepository.findById(id);
    }
}
