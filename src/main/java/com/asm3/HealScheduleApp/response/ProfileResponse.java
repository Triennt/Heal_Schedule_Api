package com.asm3.HealScheduleApp.response;

import com.asm3.HealScheduleApp.entity.Schedule;
import com.asm3.HealScheduleApp.entity.User;
import com.asm3.HealScheduleApp.validation.ValidEmail;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public class ProfileResponse extends Response{
//    private String fullName;
//
//    private String email;
//
//    private String phone;
//
//    private String gender;
//
//    private String address;
//
//    private String description;
//
//    private String avatar;

    User user;
    List<Schedule> medicalHistory;

    public ProfileResponse(int status, String message, User user, List<Schedule> medicalHistory) {
        super(status, message);
//        this.fullName = user.getFullName();
//        this.email = user.getEmail();
//        this.gender = user.getGender();
//        this.address = user.getAddress();
//        this.phone = user.getPhone();
//        this.description = user.getDescription();
//        this.avatar = user.getAvatar();
        this.medicalHistory = medicalHistory;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Schedule> getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(List<Schedule> medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
}
