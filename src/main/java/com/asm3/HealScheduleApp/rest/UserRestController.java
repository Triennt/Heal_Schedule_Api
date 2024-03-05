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
import com.asm3.HealScheduleApp.utils.MyUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
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
    @Autowired
    private MyUtils myUtils;

    /**
     * Phương thức GET được sử dụng để hiển thị hồ sơ của người dùng hiện tại.
     *
     * @return một đối tượng ResponseEntity chứa thông tin về hồ sơ của người dùng hiện tại, bao gồm thông tin người dùng và lịch sử bệnh án của họ
     */
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> showProfile(){

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(userName);
        List<Schedule> medicalHistory = scheduleService.getMedicalHistory(user);

        ProfileResponse profileResponse = new ProfileResponse(HttpStatus.OK.value(), "Profile of "+userName, user, medicalHistory);
        return new ResponseEntity<ProfileResponse>(profileResponse, HttpStatus.OK);
    }

    /**
     * Phương thức POST được sử dụng để đặt lịch hẹn cho bệnh nhân.
     *
     * @param bookRequest     Đối tượng chứa thông tin yêu cầu đặt lịch hẹn của bệnh nhân
     * @param bindingResult   Đối tượng BindingResult để kiểm tra lỗi hợp lệ của dữ liệu đầu vào
     * @return một đối tượng ResponseEntity chứa kết quả của việc đặt lịch hẹn, bao gồm thông báo thành công hoặc thông báo lỗi nếu có
     */
    @PostMapping("/book")
    public ResponseEntity<?> book(@Valid @RequestBody BookRequest bookRequest, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"Scheduling failed.", myUtils.getDescriptionErrors(bindingResult));
            return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
        }
        Schedule schedule = scheduleService.save(bookRequest);
        ScheduleResponse scheduleResponse = new ScheduleResponse(HttpStatus.OK.value(), "Scheduled successfully",schedule);
        return new ResponseEntity<ScheduleResponse>(scheduleResponse, HttpStatus.OK);
    }
}
