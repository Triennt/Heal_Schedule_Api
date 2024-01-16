package com.asm3.HealScheduleApp.rest;

import com.asm3.HealScheduleApp.entity.User;
import com.asm3.HealScheduleApp.exception.CustomNotFoundException;
import com.asm3.HealScheduleApp.model.ChangePasswordRequest;
import com.asm3.HealScheduleApp.response.ErrorResponse;
import com.asm3.HealScheduleApp.response.CreateUserResponse;
import com.asm3.HealScheduleApp.response.Response;
import com.asm3.HealScheduleApp.security.JwtTokenProvider;
import com.asm3.HealScheduleApp.service.RoleService;
import com.asm3.HealScheduleApp.service.UserService;
import com.asm3.HealScheduleApp.validation.ValidEmail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RegistrationRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private JavaMailSender mailSender;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {

        System.out.println(user.getPassword());
        System.out.println(user.getMatchingPassword());
        if (bindingResult.hasErrors()){
            // Lấy danh sách lỗi từ BindingResult
            List<FieldError> errors = bindingResult.getFieldErrors();

            // Duyệt qua danh sách lỗi và chuyển chúng sang model
            String description = "";
            for (FieldError error : errors) {
                description = error.getField() + ": " + error.getDefaultMessage() + "\n";
            }
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Registration failed.", description);

            return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
        }

        User existing = userService.findByEmail(user.getEmail());
        if (existing != null){
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"Registration failed.", "Email already exists");
            return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
        }

        userService.create(user);

        CreateUserResponse success = new CreateUserResponse(HttpStatus.CREATED.value(), "Sign Up Success.", userService.findByEmail(user.getEmail()));
        return new ResponseEntity<CreateUserResponse>(success, HttpStatus.CREATED);

    }
    @GetMapping("/forgotPassword")
    public ResponseEntity<Response> sendEmail(@RequestParam("email") String email){
        System.out.println("verifyEmail: "+email);

        User tempUser = userService.findByEmail(email);
        if (tempUser == null){
            throw new CustomNotFoundException("Email does not exist.");
        }
        String jwt = jwtTokenProvider.createToken(tempUser, "/forgotPassword");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Please verify your email");
        message.setText("Username: " +email+ "is changing the password. Use the token below for authentication when you change your password: " + jwt);
        mailSender.send(message);

        Response response = new Response(HttpStatus.OK.value(),"Email sent successfully");
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                                   HttpServletRequest request,
                                                   BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            // Lấy danh sách lỗi từ BindingResult
            List<FieldError> errors = bindingResult.getFieldErrors();

            // Duyệt qua danh sách lỗi và chuyển chúng sang model
            String description = "";
            for (FieldError error : errors) {
                description = error.getField() + ": " + error.getDefaultMessage() + "\n";
            }
            ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Change password failed.", description);

            return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        System.out.println("user name change password: "+username);

        userService.changePassword(username, changePasswordRequest);

        Response response = new Response(HttpStatus.OK.value(), "Password changed successfully.");
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }
}
