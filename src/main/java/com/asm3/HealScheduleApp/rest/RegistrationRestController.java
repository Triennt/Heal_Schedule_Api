package com.asm3.HealScheduleApp.rest;

import com.asm3.HealScheduleApp.entity.User;
import com.asm3.HealScheduleApp.response.ErrorResponse;
import com.asm3.HealScheduleApp.response.CreateUserResponse;
import com.asm3.HealScheduleApp.service.RoleService;
import com.asm3.HealScheduleApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RegistrationRestController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

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
}
