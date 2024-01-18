package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.dao.SpecializationRepository;
import com.asm3.HealScheduleApp.entity.Specialization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecializationServiceImpl implements SpecializationService{
    @Autowired
    SpecializationRepository specializationRepository;
    @Override
    public List<Specialization> getTopSpecialization() {

        Pageable pageable = PageRequest.of(0,5);
        return specializationRepository.getTopSpecialization(pageable);
    }
}
