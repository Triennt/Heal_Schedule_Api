package com.asm3.HealScheduleApp.response;

import com.asm3.HealScheduleApp.entity.Patients;

import java.util.List;

public class ListPatientsResponse extends Response{
    List<Patients> ListPatients;

    public ListPatientsResponse(int status, String message, List<Patients> listPatients) {
        super(status, message);
        ListPatients = listPatients;
    }

    public List<Patients> getListPatients() {
        return ListPatients;
    }

    public void setListPatients(List<Patients> listPatients) {
        ListPatients = listPatients;
    }
}
