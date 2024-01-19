package com.asm3.HealScheduleApp.dao;

import com.asm3.HealScheduleApp.entity.DoctorInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorInformationRepository extends JpaRepository<DoctorInformation, Long> {
    @Query("select D " +
            "from DoctorInformation D " +
            "join Specialization S on D.specialization = S " +
            "join Clinic C on S.clinic = C " +
            "where C.address like %?1% " +
            "and S.description like %?2% " +
            "and C.price <= ?3 " +
            "and C.name like %?4%")
    List<DoctorInformation> generalSearch(String area, String pathology, double price, String clinic);

    @Query("select D " +
            "from DoctorInformation D " +
            "join Specialization S on D.specialization = S " +
            "where S.name like %?1%")
    List<DoctorInformation> specializationSearch(String specialization);

}
