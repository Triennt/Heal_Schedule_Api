package com.asm3.HealScheduleApp.rest;

import com.asm3.HealScheduleApp.response.LoginResponse;
import com.asm3.HealScheduleApp.response.Response;
import com.asm3.HealScheduleApp.security.JwtTokenProvider;
import com.asm3.HealScheduleApp.body.LoginRequest;
import com.asm3.HealScheduleApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginRestController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // Xác thực từ email và password.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // Nếu không xảy ra exception tức là thông tin hợp lệ
        // Set thông tin authentication vào Security Context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.createToken(userService.findByEmail(authentication.getName()), "/login");
        LoginResponse message = new LoginResponse(HttpStatus.OK.value(),"Logged in successfully", loginRequest.getEmail(),jwt);
        return new ResponseEntity<LoginResponse>(message, HttpStatus.OK);

    }
//    @RequestMapping("/accessDenied")
//    public ResponseEntity<Response> accessDenied(){
//        System.out.println("/accessDenied");
//        Response response = new Response(HttpStatus.UNAUTHORIZED.value(),"Access denied");
//        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
//    }

}
