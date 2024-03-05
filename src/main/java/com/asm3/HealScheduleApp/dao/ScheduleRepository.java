package com.asm3.HealScheduleApp.dao;

import com.asm3.HealScheduleApp.entity.DoctorInformation;
import com.asm3.HealScheduleApp.entity.Patients;
import com.asm3.HealScheduleApp.entity.Schedule;
import com.asm3.HealScheduleApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /**
     * Truy vấn danh sách các lịch hẹn của một người dùng cụ thể.
     *
     * @param user người dùng cần tìm kiếm lịch hẹn của họ
     * @return một danh sách các lịch hẹn của người dùng được chỉ định
     */
    @Query("select S " +
            "from Schedule S " +
            "join Patients P on S.patients = P " +
            "where P.user = ?1")
    List<Schedule> getSchedulesByUser(User user);

    Schedule findById(long id);

    List<Schedule> findAllByDoctorInformation(DoctorInformation doctor);

    Schedule findByPatients(Patients patients);
}
