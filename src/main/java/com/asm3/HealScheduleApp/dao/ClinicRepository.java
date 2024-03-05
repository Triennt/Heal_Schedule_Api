package com.asm3.HealScheduleApp.dao;

import com.asm3.HealScheduleApp.entity.Clinic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    /**
     * Truy vấn danh sách các phòng khám, với số lượng bệnh nhân được sắp xếp giảm dần.
     * @param pageable số lượng kết quả
     * @return một danh sách các Clinic được sắp xếp theo số lượng bệnh nhân giảm dần
     */
    @Query("select C " +
            "from Clinic C " +
            "join Specialization S on C = S.clinic " +
            "join DoctorInformation D on D.specialization = S " +
            "join Patients P on P.doctorInformation = D " +
            "group by C " +
            "order by count(P) desc")
    List<Clinic> getTopClinic(Pageable pageable);
}
