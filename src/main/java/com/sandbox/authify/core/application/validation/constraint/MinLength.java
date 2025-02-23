package com.sandbox.authify.core.application.validation.constraint;

import com.sandbox.authify.core.application.validation.ConstraintValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class MinLength implements ConstraintValidator {

    private final Integer min;
    private final String message;

    @Override
    public boolean validate(Object value) {
        if (Objects.isNull(value)) {
            return true;
        }

        if (value instanceof String strValue) {
            return strValue.length() >= min;
        }

        throw new UnsupportedOperationException("Can't validate type of " + value.getClass().getName());
    }
}
