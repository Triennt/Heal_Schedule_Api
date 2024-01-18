package com.asm3.HealScheduleApp.dao;

import com.asm3.HealScheduleApp.entity.Schedule;
import com.asm3.HealScheduleApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("select S " +
            "from Schedule S " +
            "join Patients P on S.patients = P " +
            "where P.user = ?1")
    List<Schedule> getMedicalHistory(User user);
}
