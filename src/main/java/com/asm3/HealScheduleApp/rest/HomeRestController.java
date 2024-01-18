package com.asm3.HealScheduleApp.rest;

import com.asm3.HealScheduleApp.dao.ClinicRepository;
import com.asm3.HealScheduleApp.entity.Clinic;
import com.asm3.HealScheduleApp.entity.Specialization;
import com.asm3.HealScheduleApp.response.HomeResponse;
import com.asm3.HealScheduleApp.service.ClinicService;
import com.asm3.HealScheduleApp.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeRestController {
    @Autowired
    SpecializationService specializationService;
    @Autowired
    ClinicService clinicService;

    @GetMapping("/home")
    public ResponseEntity<HomeResponse> home(){

        List<Specialization> topSpecialization = specializationService.getTopSpecialization();
        List<Clinic> topClinic = clinicService.getTopClinic();

        HomeResponse homeResponse = new HomeResponse(HttpStatus.OK.value(),"Home Page", topSpecialization, topClinic);
        return new ResponseEntity<HomeResponse>(homeResponse, HttpStatus.OK);
    }

    @GetMapping("/home/search")
    public String search(){
        return "search";
    }
}
