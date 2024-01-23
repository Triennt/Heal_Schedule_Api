package com.asm3.HealScheduleApp.service;

import com.asm3.HealScheduleApp.entity.User;
import com.asm3.HealScheduleApp.dto.ChangePasswordRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByEmail(String email);
    User create(User user);
    void changePassword(String email, ChangePasswordRequest changePasswordRequest);
}
