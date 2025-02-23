package com.sandbox.authify.core.common.exception;

import lombok.Getter;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class ValidationException extends Exception {
    private final Map<String, Set<String>> errors;
    private String firstError = "VALIDATION_ERROR";

    public ValidationException(Map<String, Set<String>> errors) {
        this.errors = errors.entrySet().stream()
            .filter(fieldErrors -> Objects.nonNull(fieldErrors.getValue()) && !fieldErrors.getValue().isEmpty())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Map.Entry<String,Set<String>> error : this.errors.entrySet()) {
            var fieldErrors = error.getValue();
            if (!fieldErrors.isEmpty()) {
                firstError = fieldErrors.stream().findFirst().orElse(firstError);
                break;
            }
        }
    }

    @Override
    public String getMessage() {
        return firstError;
    }
}
