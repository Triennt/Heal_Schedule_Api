package com.asm3.HealScheduleApp.dao;

import com.asm3.HealScheduleApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findById(long id);
}
