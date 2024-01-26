package com.asm3.HealScheduleApp.response;

import com.asm3.HealScheduleApp.entity.Schedule;

import java.util.List;

public class ListScheduleResponse extends Response{
    List<Schedule> schedules;

    public ListScheduleResponse(int status, String message, List<Schedule> schedules) {
        super(status, message);
        this.schedules = schedules;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
