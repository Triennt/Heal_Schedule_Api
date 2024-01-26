package com.asm3.HealScheduleApp.dao;

import com.asm3.HealScheduleApp.entity.DoctorInformation;
import com.asm3.HealScheduleApp.entity.Patients;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientsRepository extends JpaRepository<Patients, Long> {
    List<Patients> findByDoctorInformation(DoctorInformation doctor);
    Patients findById(long id);
}
