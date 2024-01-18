package com.asm3.HealScheduleApp.response;

import com.asm3.HealScheduleApp.entity.Clinic;
import com.asm3.HealScheduleApp.entity.Specialization;

import java.util.List;

public class HomeResponse extends Response{
    List<Specialization> topSpecializations;
    List<Clinic> topClinic;

    public HomeResponse(int status, String message) {
        super(status, message);
    }

    public HomeResponse(int status, String message, List<Specialization> topSpecializations, List<Clinic> topClinic) {
        super(status, message);
        this.topSpecializations = topSpecializations;
        this.topClinic = topClinic;
    }

    public List<Specialization> getTopSpecializations() {
        return topSpecializations;
    }

    public void setTopSpecializations(List<Specialization> topSpecializations) {
        this.topSpecializations = topSpecializations;
    }

    public List<Clinic> getTopClinic() {
        return topClinic;
    }

    public void setTopClinic(List<Clinic> topClinic) {
        this.topClinic = topClinic;
    }
}
