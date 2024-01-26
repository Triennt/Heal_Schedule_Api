package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.dao.PatientsRepository;
import com.asm3.HealScheduleApp.entity.DoctorInformation;
import com.asm3.HealScheduleApp.entity.Patients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PatientsServiceImpl implements PatientsService{
    @Autowired
    private PatientsRepository patientsRepository;
    @Override
    public List<Patients> getPatients(DoctorInformation doctor) {
        return patientsRepository.findByDoctorInformation(doctor);
    }

    @Override
    public Patients findById(long id) {
        return patientsRepository.findById(id);
    }
}
