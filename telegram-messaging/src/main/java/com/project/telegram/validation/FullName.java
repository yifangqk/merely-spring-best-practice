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
@Constraint(validatedBy = FullName.FullNameValidator.class)
public @interface FullName {

    String message() default "Username should have at least 2 characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class FullNameValidator implements ConstraintValidator<FullName, String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return StringUtils.isNotBlank(value) && value.length() >= 2;
        }
    }
}
