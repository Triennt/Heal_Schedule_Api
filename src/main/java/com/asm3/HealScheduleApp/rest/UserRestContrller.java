package com.asm3.HealScheduleApp.rest;

import com.asm3.HealScheduleApp.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class UserRestContrller {

    @GetMapping("/user")
    public ResponseEntity<String> user(){
        System.out.println("/user");

        return new ResponseEntity<>("Hello userrrrr", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/doctor")
    public ResponseEntity<String> doctor(){
        System.out.println("/doctor");
        return new ResponseEntity<>("Hello doctor", HttpStatus.OK);
    }
}
