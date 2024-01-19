package com.asm3.HealScheduleApp.body;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequest {

    private String email;
    private String password;

}
