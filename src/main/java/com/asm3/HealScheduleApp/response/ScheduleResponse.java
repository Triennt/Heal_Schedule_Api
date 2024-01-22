package com.asm3.HealScheduleApp.response;

import com.asm3.HealScheduleApp.entity.Schedule;

public class ScheduleResponse extends Response{
    Schedule schedule;

    public ScheduleResponse(int status, String message, Schedule schedule) {
        super(status, message);
        this.schedule = schedule;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
