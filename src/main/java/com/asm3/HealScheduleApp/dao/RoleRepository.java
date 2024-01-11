package com.asm3.HealScheduleApp.dao;

import com.asm3.HealScheduleApp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
