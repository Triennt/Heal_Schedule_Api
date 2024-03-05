package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.dao.ClinicRepository;
import com.asm3.HealScheduleApp.entity.Clinic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClinicServiceImpl implements ClinicService {
    @Autowired
    private ClinicRepository clinicRepository;

    /**
     * Phương thức này được sử dụng để lấy danh sách các phòng khám hàng đầu.
     *
     * @return List<Clinic> Danh sách các phòng khám hàng đầu.
     */
    @Override
    public List<Clinic> getTopClinic() {

        // Tạo một đối tượng Pageable để chỉ định số lượng kết quả trả về và trang đầu tiên
        Pageable pageable = PageRequest.of(0,5);

        return clinicRepository.getTopClinic(pageable);
    }
}
