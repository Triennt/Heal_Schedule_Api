package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.dao.RoleRepository;
import com.asm3.HealScheduleApp.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
