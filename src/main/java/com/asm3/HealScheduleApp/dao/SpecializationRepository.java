package com.asm3.HealScheduleApp.dao;

import com.asm3.HealScheduleApp.entity.Specialization;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

    @Query("select Sp " +
            "from Specialization Sp " +
            "join DoctorInformation D on D.specialization = Sp " +
            "join Schedule Sc on Sc.doctorInformation = D " +
            "group by Sp " +
            "order by count(Sp) desc")
    List<Specialization> getTopSpecialization(Pageable pageable);

    Specialization findById(long id);
}
