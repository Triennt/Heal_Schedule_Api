package com.asm3.HealScheduleApp.response;

import com.asm3.HealScheduleApp.entity.DoctorInformation;

import java.util.List;

public class SearchResponse extends Response{
    List<DoctorInformation> doctors;

    public SearchResponse(int status, String message, List<DoctorInformation> doctors) {
        super(status, message);
        this.doctors = doctors;
    }

    public List<DoctorInformation> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorInformation> doctors) {
        this.doctors = doctors;
    }
}
