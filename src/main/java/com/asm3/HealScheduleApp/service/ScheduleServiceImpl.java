package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.dto.BookRequest;
import com.asm3.HealScheduleApp.dao.DoctorInformationRepository;
import com.asm3.HealScheduleApp.dao.PatientsRepository;
import com.asm3.HealScheduleApp.dao.ScheduleRepository;
import com.asm3.HealScheduleApp.dao.UserRepository;
import com.asm3.HealScheduleApp.entity.DoctorInformation;
import com.asm3.HealScheduleApp.entity.Patients;
import com.asm3.HealScheduleApp.entity.Schedule;
import com.asm3.HealScheduleApp.entity.User;
import com.asm3.HealScheduleApp.exception.CustomNotFoundException;
import com.asm3.HealScheduleApp.exception.ValueNotMatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ScheduleServiceImpl implements ScheduleService{
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private PatientsRepository patientsRepository;
    @Autowired
    private DoctorInformationRepository doctorInformationRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Schedule> getMedicalHistory(User user) {
        return scheduleRepository.getSchedulesByUser(user);
    }

    @Override
    public Schedule save(BookRequest bookRequest) {

        DoctorInformation doctor = doctorInformationRepository.findById((long) bookRequest.getIdDoctor());
        if (doctor == null){
            throw new CustomNotFoundException("Could not find a doctor with id = "+bookRequest.getIdDoctor());
        }
        if (bookRequest.getPrice() != doctor.getSpecialization().getClinic().getPrice()){
            throw new ValueNotMatchException("Medical examination price is not match");
        }

        Patients patients = bookRequest.getPatients();
        patients.setDoctorInformation(doctor);
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        patients.setUser(userRepository.findByEmail(userName));

        Schedule schedule = new Schedule();
        schedule.setId(0);
        schedule.setPatients(patientsRepository.save(patients));
        schedule.setDoctorInformation(doctor);
        schedule.setDate(bookRequest.getDate());
        schedule.setTime(bookRequest.getTime());
        schedule.setPrice(bookRequest.getPrice());
        schedule.setCreatedAt(LocalDateTime.now());

        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule save(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule findById(long id) {
        return scheduleRepository.findById(id);
    }

    @Override
    public List<Schedule> getSchedulesOfDoctor(DoctorInformation doctor) {
        return scheduleRepository.findAllByDoctorInformation(doctor);
    }

    @Override
    public Schedule getScheduleOfPatients(Patients patients) {
        return scheduleRepository.findByPatients(patients);
    }
}
