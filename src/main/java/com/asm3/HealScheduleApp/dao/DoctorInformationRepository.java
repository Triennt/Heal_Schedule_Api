package com.asm3.HealScheduleApp.dao;

import com.asm3.HealScheduleApp.entity.DoctorInformation;
import com.asm3.HealScheduleApp.entity.Schedule;
import com.asm3.HealScheduleApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorInformationRepository extends JpaRepository<DoctorInformation, Long> {

    /**
     * Tìm kiếm tổng quát các thông tin của các bác sĩ dựa trên nhiều tiêu chí như khu vực, bệnh lý, giá cả và tên phòng khám.
     *
     * @param area       khu vực cần tìm kiếm (có thể là một phần của địa chỉ của phòng khám)
     * @param pathology  bệnh lý cần tìm kiếm (có thể là một phần của mô tả chuyên khoa)
     * @param price      giá cả tối đa của phòng khám cần tìm kiếm
     * @param clinic     tên của phòng khám cần tìm kiếm (có thể là một phần của tên phòng khám)
     * @return một danh sách các DoctorInformation thỏa mãn các tiêu chí tìm kiếm
     */
    @Query("select D " +
            "from DoctorInformation D " +
            "join Specialization S on D.specialization = S " +
            "join Clinic C on S.clinic = C " +
            "where C.address like %?1% " +
            "and S.description like %?2% " +
            "and C.price <= ?3 " +
            "and C.name like %?4%")
    List<DoctorInformation> generalSearch(String area, String pathology, double price, String clinic);

    /**
     * Tìm kiếm các thông tin của các bác sĩ theo chuyên khoa.
     *
     * @param specialization tên chuyên khoa cần tìm kiếm (có thể là một phần của tên chuyên khoa)
     * @return một danh sách các DoctorInformation thuộc chuyên khoa cần tìm kiếm
     */
    @Query("select D " +
            "from DoctorInformation D " +
            "join Specialization S on D.specialization = S " +
            "where S.name like %?1%")
    List<DoctorInformation> specializationSearch(String specialization);

    DoctorInformation findById(long id);

    DoctorInformation findByUser(User user);

}
