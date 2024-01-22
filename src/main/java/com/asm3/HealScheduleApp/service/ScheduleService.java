package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.body.BookRequest;
import com.asm3.HealScheduleApp.entity.Schedule;
import com.asm3.HealScheduleApp.entity.User;

import java.util.List;

public interface ScheduleService {
    List<Schedule> getMedicalHistory(User user);

    Schedule save(BookRequest bookRequest);
}
