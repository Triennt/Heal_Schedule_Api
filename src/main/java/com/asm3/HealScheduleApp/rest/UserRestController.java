package com.asm3.HealScheduleApp.rest;

import com.asm3.HealScheduleApp.dto.BookRequest;
import com.asm3.HealScheduleApp.entity.Schedule;
import com.asm3.HealScheduleApp.entity.User;
import com.asm3.HealScheduleApp.response.ScheduleResponse;
import com.asm3.HealScheduleApp.response.ErrorResponse;
import com.asm3.HealScheduleApp.response.ProfileResponse;
import com.asm3.HealScheduleApp.service.DoctorInformationService;
import com.asm3.HealScheduleApp.service.ScheduleService;
import com.asm3.HealScheduleApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserRestController {
    @Autowired
    private UserService userService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private DoctorInformationService doctorInformationService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> showProfile(){

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(userName);
        List<Schedule> medicalHistory = scheduleService.getMedicalHistory(user);

        ProfileResponse profileResponse = new ProfileResponse(HttpStatus.OK.value(), "Profile of "+userName, user, medicalHistory);
        return new ResponseEntity<ProfileResponse>(profileResponse, HttpStatus.OK);
    }

    @PostMapping("/book")
    public ResponseEntity<?> book(@Valid @RequestBody BookRequest bookRequest, BindingResult bindingResult){

        String errorDescription = null;

        if (bindingResult.hasErrors()){
            // Lấy danh sách lỗi từ BindingResult
            List<FieldError> errors = bindingResult.getFieldErrors();

            // Duyệt qua danh sách lỗi và chuyển chúng sang model
            String description = "";
            for (FieldError error : errors) {
                description = description + error.getField() + ": " + error.getDefaultMessage() + "\n";
            }
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"Scheduling failed.", description);
            return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
        }
//        DoctorInformation doctor = doctorInformationService.findById(bookRequest.getIdDoctor());
//        if (doctor == null){
//            errorDescription = "Could not find a doctor with id = "+bookRequest.getIdDoctor();
//        }
//        if (bookRequest.getPrice() != doctor.getSpecialization().getClinic().getPrice()){
//            errorDescription = "Medical examination price is not correct";
//        }
//        if (errorDescription != null){
//            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Scheduling failed.", "Could not find a doctor with id = "+bookRequest.getIdDoctor());
//            return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
//        } else {
//            Schedule schedule = scheduleService.save(bookRequest);
//            BookResponse bookResponse = new BookResponse(HttpStatus.OK.value(), "Scheduled successfully",schedule);
//            return new ResponseEntity<BookResponse>(bookResponse, HttpStatus.OK);
//        }
        Schedule schedule = scheduleService.save(bookRequest);
        ScheduleResponse scheduleResponse = new ScheduleResponse(HttpStatus.OK.value(), "Scheduled successfully",schedule);
        return new ResponseEntity<ScheduleResponse>(scheduleResponse, HttpStatus.OK);
    }
}
