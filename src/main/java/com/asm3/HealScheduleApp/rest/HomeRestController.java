package com.asm3.HealScheduleApp.rest;

import com.asm3.HealScheduleApp.body.GeneralSearchRequest;
import com.asm3.HealScheduleApp.entity.Clinic;
import com.asm3.HealScheduleApp.entity.DoctorInformation;
import com.asm3.HealScheduleApp.entity.Specialization;
import com.asm3.HealScheduleApp.response.SearchResponse;
import com.asm3.HealScheduleApp.response.HomeResponse;
import com.asm3.HealScheduleApp.service.ClinicService;
import com.asm3.HealScheduleApp.service.DoctorInformationService;
import com.asm3.HealScheduleApp.service.SpecializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HomeRestController {
    @Autowired
    private SpecializationService specializationService;
    @Autowired
    private ClinicService clinicService;
    @Autowired
    private DoctorInformationService doctorInformationService;

    @GetMapping("/home")
    public ResponseEntity<HomeResponse> home(){

        List<Specialization> topSpecialization = specializationService.getTopSpecialization();
        List<Clinic> topClinic = clinicService.getTopClinic();

        HomeResponse homeResponse = new HomeResponse(HttpStatus.OK.value(),"Home Page", topSpecialization, topClinic);
        return new ResponseEntity<HomeResponse>(homeResponse, HttpStatus.OK);
    }

    @GetMapping("/home/search/general")
    public ResponseEntity<SearchResponse> generalSearch(@RequestBody GeneralSearchRequest generalSearchRequest){

        List<DoctorInformation> doctors = doctorInformationService.generalSearch(generalSearchRequest);
        SearchResponse searchResponse = new SearchResponse(HttpStatus.OK.value(), "Search results",doctors);

        return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.OK);
    }

    @GetMapping("/home/search/specialization")
    public ResponseEntity<SearchResponse> specializationSearch(@RequestParam String specialization){

        List<DoctorInformation> doctors = doctorInformationService.specializationSearch(specialization);
        SearchResponse searchResponse = new SearchResponse(HttpStatus.OK.value(), "Search results",doctors);

        return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.OK);
    }

}
