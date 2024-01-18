package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.dao.ScheduleRepository;
import com.asm3.HealScheduleApp.entity.Schedule;
import com.asm3.HealScheduleApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ScheduleServiceImpl implements ScheduleService{
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Override
    public List<Schedule> getMedicalHistory(User user) {
        return scheduleRepository.getMedicalHistory(user);
    }
}
