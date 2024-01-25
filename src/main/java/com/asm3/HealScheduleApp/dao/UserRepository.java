package com.asm3.HealScheduleApp.dao;

import com.asm3.HealScheduleApp.entity.User;
import com.asm3.HealScheduleApp.validation.ValidPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findById(long id);
}
