package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.entity.Role;

public interface RoleService {

    Role findByName(String name);
}
