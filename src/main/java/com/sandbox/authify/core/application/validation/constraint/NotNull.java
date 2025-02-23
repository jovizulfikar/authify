package com.sandbox.authify.core.application.validation.constraint;

import com.sandbox.authify.core.application.validation.ConstraintValidator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Getter
@RequiredArgsConstructor
public class NotNull implements ConstraintValidator {

    private final String message;

    @Override
    public boolean validate(Object value) {
        return Objects.nonNull(value);
    }
    
}
