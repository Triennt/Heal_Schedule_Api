package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.dao.SpecializationRepository;
import com.asm3.HealScheduleApp.dto.AddDoctorRequest;
import com.asm3.HealScheduleApp.dto.GeneralSearchRequest;
import com.asm3.HealScheduleApp.dao.DoctorInformationRepository;
import com.asm3.HealScheduleApp.dao.UserRepository;
import com.asm3.HealScheduleApp.entity.DoctorInformation;
import com.asm3.HealScheduleApp.entity.Specialization;
import com.asm3.HealScheduleApp.entity.User;
import com.asm3.HealScheduleApp.exception.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DoctorInformationServiceImpl implements DoctorInformationService{
    @Autowired
    private DoctorInformationRepository doctorInformationRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SpecializationRepository specializationRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Override
    public List<DoctorInformation> generalSearch(GeneralSearchRequest generalSearchRequest) {
        String area = generalSearchRequest.getArea();
        String pathology = generalSearchRequest.getPathology();
        double price = generalSearchRequest.getPrice();
        String clinic = generalSearchRequest.getClinic();

        return doctorInformationRepository.generalSearch(area,pathology,price,clinic);
    }

    @Override
    public List<DoctorInformation> specializationSearch(String specialization) {
        return doctorInformationRepository.specializationSearch(specialization);
    }

    @Override
    public DoctorInformation findById(long id) {
        return doctorInformationRepository.findById(id);
    }

    @Override
    public DoctorInformation findByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return doctorInformationRepository.findByUser(user);
    }

    @Override
    public DoctorInformation save(AddDoctorRequest doctorRequest) {

        Specialization specialization = specializationRepository.findById(doctorRequest.getSpecializationId());
        if (specialization == null)
            throw new CustomNotFoundException("Could not find a specialization with id = "+doctorRequest.getSpecializationId());

        User userDoctor = doctorRequest.getUser();
        userDoctor.setId(0);
        userDoctor.setRoles(Arrays.asList(roleService.findByName("ROLE_DOCTOR")));
        userDoctor.setPassword(passwordEncoder.encode(userDoctor.getPassword()));
        userDoctor.setMatchingPassword(userDoctor.getPassword());

        DoctorInformation doctor = new DoctorInformation();
        doctor.setId(0);
        doctor.setAchievements(doctorRequest.getAchievements());
        doctor.setIntroduce(doctorRequest.getIntroduce());
        doctor.setTrainingProcess(doctorRequest.getTrainingProcess());
        doctor.setSpecialization(specialization);
        doctor.setUser(userDoctor);

        return doctorInformationRepository.save(doctor);
    }

    @Override
    public DoctorInformation getDoctorSession() {
        String emailDoctor = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByEmail(emailDoctor);
    }
}
