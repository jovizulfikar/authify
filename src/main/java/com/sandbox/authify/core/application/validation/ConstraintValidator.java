package com.sandbox.authify.core.application.validation;

public interface ConstraintValidator {
    boolean validate(Object value);
    String getMessage();
}
