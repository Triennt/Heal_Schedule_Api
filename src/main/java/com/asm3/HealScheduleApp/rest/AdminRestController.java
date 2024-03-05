package com.asm3.HealScheduleApp.rest;

import com.asm3.HealScheduleApp.dto.AddDoctorRequest;
import com.asm3.HealScheduleApp.entity.DoctorInformation;
import com.asm3.HealScheduleApp.entity.Patients;
import com.asm3.HealScheduleApp.entity.Schedule;
import com.asm3.HealScheduleApp.entity.User;
import com.asm3.HealScheduleApp.exception.CustomNotFoundException;
import com.asm3.HealScheduleApp.response.*;
import com.asm3.HealScheduleApp.service.DoctorInformationService;
import com.asm3.HealScheduleApp.service.PatientsService;
import com.asm3.HealScheduleApp.service.ScheduleService;
import com.asm3.HealScheduleApp.service.UserService;
import com.asm3.HealScheduleApp.utils.MyUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    private PatientsService patientsService;
    @Autowired
    private ScheduleService scheduleService;

    /**
     * Phương thức POST để thêm thông tin của một bác sĩ mới vào hệ thống.
     *
     * @param doctor         yêu cầu chứa thông tin của bác sĩ mới cần được thêm vào
     * @param bindingResult  kết quả của việc kiểm tra ràng buộc dữ liệu, được sử dụng để kiểm tra lỗi
     * @return một đối tượng ResponseEntity chứa kết quả của việc thêm bác sĩ mới, bao gồm cả thông tin thành công hoặc lỗi nếu có
     */
    @PostMapping("/addDoctor")
    public ResponseEntity<?> addDoctor(@Valid @RequestBody AddDoctorRequest doctor, BindingResult bindingResult){

        // Kiểm tra nếu có lỗi trong dữ liệu đầu vào
        if (bindingResult.hasErrors()){
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"Add doctor account failed.", myUtils.getDescriptionErrors(bindingResult));
            return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        User existing = userService.findByEmail(doctor.getUser().getEmail());
        if (existing != null){
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"Add doctor account failed.", "Email already exists");
            return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
        }

        // Lưu thông tin của bác sĩ mới vào hệ thống và tạo phản hồi thành công
        AddDoctorResponse addDoctorResponse = new AddDoctorResponse(HttpStatus.OK.value(), "Doctor account added successfully",doctorInformationService.save(doctor));
        return new ResponseEntity<AddDoctorResponse>(addDoctorResponse, HttpStatus.OK);
    }

    /**
     * Phương thức PUT được sử dụng để khóa tài khoản của một người dùng với một lý do cụ thể.
     *
     * @param userId   ID của người dùng cần khóa
     * @param reason   Lý do cho việc khóa tài khoản
     * @return một đối tượng ResponseEntity chứa kết quả của việc khóa tài khoản, bao gồm cả thông tin thành công hoặc lỗi nếu có
     * @throws CustomNotFoundException Nếu không tìm thấy tài khoản với ID được chỉ định
     */
    @PutMapping("/lockAccount/{userId}")
    public ResponseEntity<?> lockAccount(@PathVariable long userId, @RequestParam String reason){

        User user = userService.findById(userId);

        if (user != null){

            // Khóa tài khoản bằng cách đặt trạng thái hoạt động thành false và cập nhật lý do khóa
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

    /**
     * Phương thức PUT được sử dụng để mở khóa tài khoản của một người dùng.
     *
     * @param userId   ID của người dùng cần mở khóa
     * @return một đối tượng ResponseEntity chứa kết quả của việc mở khóa tài khoản, bao gồm cả thông tin thành công hoặc lỗi nếu có
     * @throws CustomNotFoundException Nếu không tìm thấy tài khoản với ID được chỉ định
     */
    @PutMapping("/unlockAccount/{userId}")
    public ResponseEntity<?> unlockAccount(@PathVariable long userId){

        User user = userService.findById(userId);

        if (user != null){

            // Mở khóa tài khoản bằng cách đặt trạng thái hoạt động thành true và cập nhật trạng thái hoạt động và mô tả
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

    /**
     * Phương thức GET được sử dụng để lấy lịch hẹn của một bệnh nhân dựa trên ID của họ.
     *
     * @param patientsId   ID của bệnh nhân mà bạn muốn lấy lịch hẹn của họ
     * @return một đối tượng ResponseEntity chứa kết quả của việc lấy lịch hẹn của bệnh nhân, bao gồm cả thông tin lịch hẹn hoặc thông báo lỗi nếu có
     * @throws CustomNotFoundException Nếu không tìm thấy bệnh nhân với ID được chỉ định
     */
    @GetMapping("/schedule/patients/{patientsId}")
    public ResponseEntity<?> scheduleOfPatients(@PathVariable long patientsId){

        Patients patients = patientsService.findById(patientsId);

        // Kiểm tra xem người dùng có tồn tại không hoặc có phải là một bệnh nhân không
        if (patients == null)
            throw new CustomNotFoundException("No found patients with id = "+patientsId);

        Schedule schedule = scheduleService.getScheduleOfPatients(patients);
        ScheduleResponse scheduleResponse = new ScheduleResponse(HttpStatus.OK.value(), "details of patient "+patients.getFullName()+"'s examination schedule",schedule);
        return new ResponseEntity<ScheduleResponse>(scheduleResponse,HttpStatus.OK);
    }

    /**
     * Phương thức GET được sử dụng để lấy lịch hẹn của một bác sĩ dựa trên ID của họ.
     *
     * @param doctorId   ID của bác sĩ mà bạn muốn lấy lịch hẹn của họ
     * @return một đối tượng ResponseEntity chứa kết quả của việc lấy lịch hẹn của bác sĩ, bao gồm cả thông tin lịch hẹn hoặc thông báo lỗi nếu có
     * @throws CustomNotFoundException Nếu không tìm thấy bác sĩ với ID được chỉ định
     */
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
