package com.asm3.HealScheduleApp.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Component
public class MyUtils {

    /**
     * Phương thức này được sử dụng để tạo một chuỗi mô tả lỗi từ BindingResult.
     *
     * @param bindingResult BindingResult: Đối tượng chứa thông tin về các lỗi sau quá trình ràng buộc.
     * @return String Chuỗi mô tả lỗi được tạo từ BindingResult.
     */
    public String getDescriptionErrors(BindingResult bindingResult){
        // Lấy danh sách lỗi từ BindingResult
        List<FieldError> errors = bindingResult.getFieldErrors();

        // Duyệt qua danh sách lỗi
        String description = "";
        for (FieldError error : errors) {
            description =description + error.getField() + ": " + error.getDefaultMessage() + "\n";
        }
        return description;
    }
}
