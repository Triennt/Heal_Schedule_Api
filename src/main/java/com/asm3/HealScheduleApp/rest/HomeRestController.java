package com.asm3.HealScheduleApp.rest;

import com.asm3.HealScheduleApp.dto.GeneralSearchRequest;
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

    /**
     * Phương thức GET được sử dụng để truy xuất trang chính của ứng dụng, hiển thị thông tin tổng quan về các chuyên môn hàng đầu và các phòng khám hàng đầu.
     *
     * @return một đối tượng ResponseEntity chứa kết quả của việc truy xuất trang chính, bao gồm danh sách các chuyên môn hàng đầu và các phòng khám hàng đầu
     */
    @GetMapping("/home")
    public ResponseEntity<HomeResponse> home(){

        List<Specialization> topSpecialization = specializationService.getTopSpecialization();
        List<Clinic> topClinic = clinicService.getTopClinic();

        HomeResponse homeResponse = new HomeResponse(HttpStatus.OK.value(),"Home Page", topSpecialization, topClinic);
        return new ResponseEntity<HomeResponse>(homeResponse, HttpStatus.OK);
    }

    /**
     * Phương thức GET được sử dụng để thực hiện tìm kiếm tổng quát dựa trên các yêu cầu tìm kiếm.
     *
     * @param generalSearchRequest   Đối tượng chứa thông tin về yêu cầu tìm kiếm tổng quát
     * @return một đối tượng ResponseEntity chứa kết quả của việc tìm kiếm tổng quát, bao gồm thông tin về các bác sĩ phù hợp với yêu cầu tìm kiếm
     */
    @GetMapping("/home/search/general")
    public ResponseEntity<SearchResponse> generalSearch(@RequestBody GeneralSearchRequest generalSearchRequest){

        List<DoctorInformation> doctors = doctorInformationService.generalSearch(generalSearchRequest);
        SearchResponse searchResponse = new SearchResponse(HttpStatus.OK.value(), "Search results",doctors);

        return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.OK);
    }

    /**
     * Phương thức GET được sử dụng để thực hiện tìm kiếm bác sĩ theo chuyên môn.
     *
     * @param specialization   Chuyên môn mà bạn muốn tìm kiếm bác sĩ
     * @return một đối tượng ResponseEntity chứa kết quả của việc tìm kiếm theo chuyên môn, bao gồm thông tin về các bác sĩ có chuyên môn tương ứng
     */
    @GetMapping("/home/search/specialization")
    public ResponseEntity<SearchResponse> specializationSearch(@RequestParam String specialization){

        List<DoctorInformation> doctors = doctorInformationService.specializationSearch(specialization);
        SearchResponse searchResponse = new SearchResponse(HttpStatus.OK.value(), "Search results",doctors);

        return new ResponseEntity<SearchResponse>(searchResponse, HttpStatus.OK);
    }

}
