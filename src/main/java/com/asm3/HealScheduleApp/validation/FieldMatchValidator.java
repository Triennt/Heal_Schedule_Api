package com.asm3.HealScheduleApp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Class FieldMatchValidator là một validator được sử dụng để kiểm tra sự khớp nhau giữa hai trường dữ liệu.
 * Nó được áp dụng thông qua annotation @FieldMatch và được sử dụng trong các trường hợp như xác nhận mật khẩu.
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
	private String firstFieldName;
    private String secondFieldName;
    private String message;

    /**
     * Phương thức khởi tạo, được gọi khi validator được khởi tạo.
     * @param constraintAnnotation Tham số chứa thông tin từ annotation @FieldMatch.
     */
    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    /**
     * Phương thức kiểm tra sự khớp nhau giữa hai trường dữ liệu.
     * @param value Đối tượng chứa các trường dữ liệu cần kiểm tra.
     * @param context Context của ConstraintValidator.
     * @return boolean Kết quả của việc kiểm tra sự khớp nhau giữa hai trường dữ liệu.
     */
    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        try
        {
            final Object firstObj = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
            final Object secondObj = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);

            valid =  firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        }
        catch (final Exception ignore)
        {

        }

        if (!valid){

            // Thêm lỗi vào context và tắt thông báo lỗi mặc định
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(secondFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
	
}