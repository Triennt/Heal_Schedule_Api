package com.asm3.HealScheduleApp.rest;

import com.asm3.HealScheduleApp.dto.AddDoctorRequest;
import com.asm3.HealScheduleApp.entity.DoctorInformation;
import com.asm3.HealScheduleApp.entity.Schedule;
import com.asm3.HealScheduleApp.entity.User;
import com.asm3.HealScheduleApp.exception.CustomNotFoundException;
import com.asm3.HealScheduleApp.response.AddDoctorResponse;
import com.asm3.HealScheduleApp.response.ErrorResponse;
import com.asm3.HealScheduleApp.response.ListScheduleResponse;
import com.asm3.HealScheduleApp.response.Response;
import com.asm3.HealScheduleApp.service.DoctorInformationService;
import com.asm3.HealScheduleApp.service.ScheduleService;
import com.asm3.HealScheduleApp.service.UserService;
import com.asm3.HealScheduleApp.utils.MyUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminRestController {
    @Autowired
    private MyUtils myUtils;
    @Autowired
    private DoctorInformationService doctorInformationService;
    @Autowired
    private UserService userService;
    @Autowired
    private ScheduleService scheduleService;
    @PostMapping("/addDoctor")
    public ResponseEntity<?> addDoctor(@Valid @RequestBody AddDoctorRequest doctor, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"Add doctor account failed", myUtils.getDescriptionErrors(bindingResult));
            return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        System.out.println("addDoctor method: "+doctor.toString());

        AddDoctorResponse addDoctorResponse = new AddDoctorResponse(HttpStatus.OK.value(), "Doctor account added successfully",doctorInformationService.save(doctor));
        return new ResponseEntity<AddDoctorResponse>(addDoctorResponse, HttpStatus.OK);
    }

    @PutMapping("/lockAccount")
    public ResponseEntity<?> lockAccount(@RequestParam long userId, @RequestParam String reason){

        User user = userService.findById(userId);

        if (user != null){

            user.getActiveStatus().setActive(false);
            user.getActiveStatus().setName("Lock");
            user.getActiveStatus().setDescription(reason);

            user = userService.save(user);
            Response response = new Response(HttpStatus.OK.value(), "Successfully locked "+user.getEmail()+" account");
            return new ResponseEntity<Response>(response, HttpStatus.OK);

        } else {
            throw new CustomNotFoundException("No account found with id = "+userId);
        }
    }
    @PutMapping("/unlockAccount")
    public ResponseEntity<?> unlockAccount(@RequestParam long userId){

        User user = userService.findById(userId);

        if (user != null){

            user.getActiveStatus().setActive(true);
            user.getActiveStatus().setName("Active");
            user.getActiveStatus().setDescription("Active account");

            user = userService.save(user);
            Response response = new Response(HttpStatus.OK.value(), "Successfully unblocked "+user.getEmail()+" account");
            return new ResponseEntity<Response>(response, HttpStatus.OK);

        } else {
            throw new CustomNotFoundException("No account found with id = "+userId);
        }
    }
    @GetMapping("/schedule/patients/{userId}")
    public ResponseEntity<?> scheduleOfPatients(@PathVariable long userId){

        User user = userService.findById(userId);
        if (user == null || user.getRoles().stream().noneMatch(role -> role.getName().equals("ROLE_USER")))
            throw new CustomNotFoundException("No found patients with id = "+userId);
        List<Schedule> schedules = scheduleService.getMedicalHistory(user);
        ListScheduleResponse listScheduleResponse = new ListScheduleResponse(HttpStatus.OK.value(), "details of patient "+user.getFullName()+"'s examination schedule",schedules);
        return new ResponseEntity<ListScheduleResponse>(listScheduleResponse,HttpStatus.OK);
    }
    @GetMapping("/schedule/doctor/{doctorId}")
    public ResponseEntity<?> scheduleOfDoctor(@PathVariable long doctorId){
        DoctorInformation doctor = doctorInformationService.findById(doctorId);
        if (doctor == null)
            throw new CustomNotFoundException("No found doctor with id = "+doctorId);

        List<Schedule> schedules = scheduleService.getSchedulesOfDoctor(doctor);
        ListScheduleResponse listScheduleResponse = new ListScheduleResponse(HttpStatus.OK.value(), "details of doctor "+doctor.getUser().getFullName()+"'s examination schedule",schedules);
        return new ResponseEntity<ListScheduleResponse>(listScheduleResponse,HttpStatus.OK);
    }


}
