package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.dto.BookRequest;
import com.asm3.HealScheduleApp.entity.DoctorInformation;
import com.asm3.HealScheduleApp.entity.Patients;
import com.asm3.HealScheduleApp.entity.Schedule;
import com.asm3.HealScheduleApp.entity.User;

import java.util.List;

public interface ScheduleService {
    List<Schedule> getMedicalHistory(User user);
    Schedule save(BookRequest bookRequest);
    Schedule save(Schedule schedule);
    Schedule findById(long id);
    List<Schedule> getSchedulesOfDoctor(DoctorInformation doctor);
    Schedule getScheduleOfPatients(Patients patients);
}
