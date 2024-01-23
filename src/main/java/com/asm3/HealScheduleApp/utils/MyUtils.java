package com.asm3.HealScheduleApp.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Component
public class MyUtils {
    public String getDescriptionErrors(BindingResult bindingResult){
        // Lấy danh sách lỗi từ BindingResult
        List<FieldError> errors = bindingResult.getFieldErrors();

        // Duyệt qua danh sách lỗi và chuyển chúng sang model
        String description = "";
        for (FieldError error : errors) {
            description =description + error.getField() + ": " + error.getDefaultMessage() + "\n";
        }
        return description;
    }
}
