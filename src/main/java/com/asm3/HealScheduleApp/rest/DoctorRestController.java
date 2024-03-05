package com.asm3.HealScheduleApp.rest;

import com.asm3.HealScheduleApp.entity.DoctorInformation;
import com.asm3.HealScheduleApp.entity.Patients;
import com.asm3.HealScheduleApp.entity.Schedule;
import com.asm3.HealScheduleApp.exception.CustomNotFoundException;
import com.asm3.HealScheduleApp.exception.InvalidValueException;
import com.asm3.HealScheduleApp.response.*;
import com.asm3.HealScheduleApp.service.DoctorInformationService;
import com.asm3.HealScheduleApp.service.PatientsService;
import com.asm3.HealScheduleApp.service.ScheduleService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * Phương thức GET được sử dụng để hiển thị danh sách bệnh nhân của bác sĩ.
     *
     * @return một đối tượng ResponseEntity chứa kết quả của việc hiển thị danh sách bệnh nhân, bao gồm cả thông tin bệnh nhân hoặc thông báo lỗi nếu có
     */
    @GetMapping("/patients")
    public ResponseEntity<?> showPatients(){
        List<Patients> patients = patientsService.getPatients(doctorInformationService.getDoctorSession());
        ListPatientsResponse listPatientsResponse = new ListPatientsResponse(HttpStatus.OK.value(), "List Patients",patients);
        return new ResponseEntity<ListPatientsResponse>(listPatientsResponse,HttpStatus.OK);
    }

    /**
     * Phương thức GET được sử dụng để hiển thị danh sách lịch hẹn của bác sĩ.
     *
     * @return một đối tượng ResponseEntity chứa kết quả của việc hiển thị danh sách lịch hẹn, bao gồm cả thông tin lịch hẹn hoặc thông báo lỗi nếu có
     */
    @GetMapping("/schedules")
    public ResponseEntity<?> showSchedules(){
        List<Schedule> schedules = scheduleService.getSchedulesOfDoctor(doctorInformationService.getDoctorSession());
        ListScheduleResponse listScheduleResponse = new ListScheduleResponse(HttpStatus.OK.value(), "List schedules", schedules);
        return new ResponseEntity<ListScheduleResponse>(listScheduleResponse, HttpStatus.OK);
    }

    /**
     * Phương thức PUT được sử dụng để chấp nhận một lịch hẹn cụ thể.
     *
     * @param scheduleId   ID của lịch hẹn mà bác sĩ muốn chấp nhận
     * @return một đối tượng ResponseEntity chứa kết quả của việc chấp nhận lịch hẹn, bao gồm cả thông tin lịch hẹn đã được chấp nhận hoặc thông báo lỗi nếu có
     * @throws CustomNotFoundException Nếu không tìm thấy lịch hẹn với ID được chỉ định
     */
    @PutMapping("/schedule/accept/{scheduleId}")
    public ResponseEntity<?> acceptSchedule(@PathVariable long scheduleId){

        DoctorInformation doctor = doctorInformationService.getDoctorSession();
        Schedule schedule = scheduleService.findById(scheduleId);

        if (schedule == null)
            throw new CustomNotFoundException("No found schedule with id = "+scheduleId);

        // Kiểm tra xem thông tin của bác sĩ khớp với thông tin trong lịch hẹn hay không
        if (doctor.getId() == schedule.getDoctorInformation().getId()){

            // Nếu khớp, cập nhật trạng thái của lịch hẹn thành "Accepted"
            schedule.getStatus().setName("Accepted");
            schedule.getStatus().setDescription("Schedule is accepted");
            schedule.getStatus().setUpdatedAt(LocalDateTime.now());
            schedule = scheduleService.save(schedule);

            ScheduleResponse scheduleResponse = new ScheduleResponse(HttpStatus.OK.value(), "Schedule received",schedule);
            return new ResponseEntity<ScheduleResponse>(scheduleResponse, HttpStatus.OK);
        } else {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Get scheduled failure","Doctor information does not match");
            return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Phương thức PUT được sử dụng để hủy bỏ một lịch hẹn cụ thể với một lý do cụ thể.
     *
     * @param scheduleId   ID của lịch hẹn mà bác sĩ muốn hủy bỏ
     * @param reason       Lý do cho việc hủy bỏ lịch hẹn
     * @return một đối tượng ResponseEntity chứa kết quả của việc hủy bỏ lịch hẹn, bao gồm cả thông tin lịch hẹn đã bị hủy bỏ hoặc thông báo lỗi nếu có
     * @throws CustomNotFoundException Nếu không tìm thấy lịch hẹn với ID được chỉ định
     */
    @PutMapping("/schedule/cancel/{scheduleId}")
    public ResponseEntity<?> cancelSchedule(@PathVariable long scheduleId,
                                            @RequestParam String reason){

        DoctorInformation doctor = doctorInformationService.getDoctorSession();
        Schedule schedule = scheduleService.findById(scheduleId);

        if (schedule == null)
            throw new CustomNotFoundException("No found schedule with id = "+scheduleId);

        // Kiểm tra xem thông tin của bác sĩ khớp với thông tin trong lịch hẹn hay không
        if (doctor.getId() == schedule.getDoctorInformation().getId()){
            // Nếu khớp, cập nhật trạng thái của lịch hẹn thành "Cancelled" và cập nhật lý do hủy bỏ
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

    /**
     * Phương thức GET được sử dụng để gửi email chứa thông tin về cuộc khám bệnh cho một bệnh nhân cụ thể.
     *
     * @param patientsId   ID của bệnh nhân cần gửi email
     * @param file         Tệp được đính kèm chứa thông tin về cuộc khám bệnh
     * @return một đối tượng ResponseEntity chứa kết quả của việc gửi email, bao gồm cả thông báo gửi email thành công hoặc thông báo lỗi nếu có
     * @throws CustomNotFoundException Nếu không tìm thấy bệnh nhân với ID được chỉ định
     * @throws InvalidValueException   Nếu không có tệp được chọn để đính kèm
     */
    @GetMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestParam long patientsId, @RequestParam MultipartFile file){
        Patients patients = patientsService.findById(patientsId);
        if (patients == null)
            throw new CustomNotFoundException("No found patients with id = "+patientsId);
        if (file.isEmpty())
            throw new InvalidValueException("No files selected");

        try {
            // Tạo một tin nhắn email
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Thiết lập thông tin người nhận, chủ đề và nội dung của email
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
