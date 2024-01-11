package com.asm3.HealScheduleApp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;


public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
	
	private String firstFieldName;
    private String secondFieldName;
    private String message;


    @Override
    public void initialize(final FieldMatch constraintAnnotation) {
	    	firstFieldName = constraintAnnotation.first();
	    	secondFieldName = constraintAnnotation.second();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean valid = true;
        try
        {
            System.out.println("firstFieldName: " +firstFieldName);
            System.out.println("secondFieldName: " +secondFieldName);
            final Object firstObj = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
            final Object secondObj = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);

            System.out.println("firstObj: " +firstObj);
            System.out.println("secondObj: " +secondObj);
            valid =  firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
            System.out.println("valid: "+valid);
        }
        catch (final Exception ignore)
        {
            // we can ignore
        }

        if (!valid){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(secondFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
	
}