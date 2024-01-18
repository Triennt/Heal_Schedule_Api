package com.asm3.HealScheduleApp.rest;

import com.asm3.HealScheduleApp.entity.Schedule;
import com.asm3.HealScheduleApp.entity.User;
import com.asm3.HealScheduleApp.response.ProfileResponse;
import com.asm3.HealScheduleApp.service.ScheduleService;
import com.asm3.HealScheduleApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> user(){

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(userName);
        List<Schedule> medicalHistory = scheduleService.getMedicalHistory(user);

        ProfileResponse profileResponse = new ProfileResponse(HttpStatus.OK.value(), "Profile of "+userName, user, medicalHistory);
        return new ResponseEntity<ProfileResponse>(profileResponse, HttpStatus.OK);
    }
//    @GetMapping("/doctor")
//    public ResponseEntity<String> doctor(){
//        System.out.println("/doctor");
//        return new ResponseEntity<>("Hello doctor", HttpStatus.OK);
//    }
}
