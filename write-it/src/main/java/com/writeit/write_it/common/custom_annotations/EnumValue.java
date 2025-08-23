package com.writeit.write_it.common.custom_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=EnumValidator.class)
public @interface EnumValue {
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String message() default "must be a valid enum value";
    String[] allowed() default {};

}
