package com.asm3.HealScheduleApp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	private Pattern pattern;
	private Matcher matcher;
	
	// Mẫu regex cho địa chỉ email
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Override
	public boolean isValid(final String email, final ConstraintValidatorContext context) {
		
		// Biên dịch mẫu regex thành Pattern
		pattern = Pattern.compile(EMAIL_PATTERN);
		
		if (email == null) {
			return false;
		}
		
		// Sử dụng matcher để so khớp địa chỉ email với mẫu regex
		matcher = pattern.matcher(email);
		
		// Trả về true nếu địa chỉ email khớp với mẫu regex, ngược lại trả về false
		return matcher.matches();
	}

}