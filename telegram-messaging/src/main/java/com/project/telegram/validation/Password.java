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

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Password.PasswordValidator.class)
public @interface Password {

    String message() default "Password should have at least 8 characters long";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    class PasswordValidator implements ConstraintValidator<Password, String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return StringUtils.isNotBlank(value) && value.length() >= 8;
        }

    }

}
