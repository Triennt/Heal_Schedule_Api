package com.asm3.HealScheduleApp.dao;

import com.asm3.HealScheduleApp.entity.Patients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientsRepository extends JpaRepository<Patients, Long> {
}
