package com.writeit.write_it.common.custom_annotations;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<EnumValue, Enum<?>>{
    private Set<String> allowedValues;

    @Override
    public void initialize(EnumValue annotation){
        String[] allowed = annotation.allowed();
        if (allowed.length > 0) {
            allowedValues = Arrays.stream(allowed).collect(Collectors.toSet());
        } else {
            allowedValues = null;
        }
    }

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        if ("UNKNOWN".equals(value.name())) {
            return false;
        }
        if (allowedValues == null) {
            allowedValues = Arrays.stream(value.getDeclaringClass().getEnumConstants())
                                .map(e -> ((Enum<?>) e).name())
                                .collect(Collectors.toSet());
            return allowedValues.contains(value.name());
        }
        return allowedValues.contains(value.name());
    }

}
