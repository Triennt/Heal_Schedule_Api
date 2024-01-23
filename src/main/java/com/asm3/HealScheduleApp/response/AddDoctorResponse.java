package com.asm3.HealScheduleApp.response;

import com.asm3.HealScheduleApp.entity.DoctorInformation;

public class AddDoctorResponse extends Response{
    private DoctorInformation doctor;

    public AddDoctorResponse(int status, String message, DoctorInformation doctor) {
        super(status, message);
        this.doctor = doctor;
    }

    public DoctorInformation getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorInformation doctor) {
        this.doctor = doctor;
    }
}
