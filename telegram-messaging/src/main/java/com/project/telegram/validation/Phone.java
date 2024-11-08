package com.project.telegram.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.regex.Pattern;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Phone.PhoneValidator.class)
public @interface Phone {

    String message() default "Wrong phone number format";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    class PhoneValidator implements ConstraintValidator<Phone, String> {

        private static final Pattern VN_PHONE_PATTERN = Pattern.compile("((^(\\+84|\\+840|84|840|0))([35789]))+([0-9]{8})$");

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return StringUtils.isNotBlank(value) && VN_PHONE_PATTERN.matcher(value).matches();
        }

    }

}


