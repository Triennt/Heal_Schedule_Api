package com.asm3.HealScheduleApp.rest;

import com.asm3.HealScheduleApp.entity.DoctorInformation;
import com.asm3.HealScheduleApp.entity.Patients;
import com.asm3.HealScheduleApp.entity.Schedule;
import com.asm3.HealScheduleApp.exception.CustomNotFoundException;
import com.asm3.HealScheduleApp.exception.InvalidValueException;
import com.asm3.HealScheduleApp.response.ErrorResponse;
import com.asm3.HealScheduleApp.response.ListPatientsResponse;
import com.asm3.HealScheduleApp.response.Response;
import com.asm3.HealScheduleApp.response.ScheduleResponse;
import com.asm3.HealScheduleApp.service.DoctorInformationService;
import com.asm3.HealScheduleApp.service.PatientsService;
import com.asm3.HealScheduleApp.service.ScheduleService;
import com.asm3.HealScheduleApp.validation.FieldMatch;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorRestController {
    @Autowired
    private DoctorInformationService doctorInformationService;
    @Autowired
    private PatientsService patientsService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private JavaMailSender mailSender;
    @GetMapping("/patients")
    public ResponseEntity<?> showPatients(){
        String emailDoctor = SecurityContextHolder.getContext().getAuthentication().getName();
        DoctorInformation doctor = doctorInformationService.findByEmail(emailDoctor);
        List<Patients> patients = patientsService.getPatients(doctor);
        ListPatientsResponse listPatientsResponse = new ListPatientsResponse(HttpStatus.OK.value(), "List Patients",patients);
        return new ResponseEntity<ListPatientsResponse>(listPatientsResponse,HttpStatus.OK);
    }

    @PutMapping("/acceptSchedule")
    public ResponseEntity<?> acceptSchedule(@RequestParam long scheduleId){
        String emailDoctor = SecurityContextHolder.getContext().getAuthentication().getName();
        DoctorInformation doctor = doctorInformationService.findByEmail(emailDoctor);
        Schedule schedule = scheduleService.findById(scheduleId);

        if (doctor.getId() == schedule.getDoctorInformation().getId()){
            schedule.getStatus().setName("Accepted");
            schedule.getStatus().setUpdatedAt(LocalDateTime.now());
            schedule = scheduleService.save(schedule);
            ScheduleResponse scheduleResponse = new ScheduleResponse(HttpStatus.OK.value(), "Schedule received",schedule);
            return new ResponseEntity<ScheduleResponse>(scheduleResponse, HttpStatus.OK);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Get scheduled failure","Doctor information does not match");
            return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/cancelSchedule")
    public ResponseEntity<?> cancelSchedule(@RequestParam long scheduleId,
                                            @RequestParam String reason){

        String emailDoctor = SecurityContextHolder.getContext().getAuthentication().getName();
        DoctorInformation doctor = doctorInformationService.findByEmail(emailDoctor);
        Schedule schedule = scheduleService.findById(scheduleId);

        if (doctor.getId() == schedule.getDoctorInformation().getId()){
            schedule.getStatus().setName("Cancelled");
            schedule.getStatus().setDescription(reason);
            schedule.getStatus().setUpdatedAt(LocalDateTime.now());
            schedule = scheduleService.save(schedule);
            ScheduleResponse scheduleResponse = new ScheduleResponse(HttpStatus.OK.value(), "Canceled the schedule successfully",schedule);
            return new ResponseEntity<ScheduleResponse>(scheduleResponse, HttpStatus.OK);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Cancel scheduled failure","Doctor information does not match");
            return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestParam long patientsId, @RequestParam MultipartFile file){
        Patients patients = patientsService.findById(patientsId);
        if (patients == null)
            throw new CustomNotFoundException("No found patients with id = "+patientsId);
        if (file.isEmpty())
            throw new InvalidValueException("No files selected");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(patients.getUser().getEmail());
            helper.setSubject("Medical examination and treatment information");
            helper.setText("Hi " + patients.getFullName() +". We have sent the medical examination and treatment results in the attached file.");

            // Đính kèm file
            helper.addAttachment(file.getOriginalFilename(), file);

            mailSender.send(message);
            Response response = new Response(HttpStatus.OK.value(), "Email sent successfully");
            return new ResponseEntity<Response>(response, HttpStatus.OK);

        } catch (MessagingException e) {
            ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Email sending failed", e.getMessage());
            return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
