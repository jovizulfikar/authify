package com.sandbox.authify.core.common.exception;

import lombok.Getter;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class ValidationException extends Exception {

    @Getter
    private final Map<String, Set<String>> errors;

    private final AtomicReference<String> firstError = new AtomicReference<>("VALIDATION_ERROR");

    public ValidationException(Map<String, Set<String>> errors) {
        this.errors = errors.entrySet().stream()
            .filter(fieldErrors -> Objects.nonNull(fieldErrors.getValue()) && !fieldErrors.getValue().isEmpty())
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        for (Map.Entry<String,Set<String>> error : this.errors.entrySet()) {
            var fieldErrors = error.getValue();
            if (!fieldErrors.isEmpty()) {
                firstError.set(fieldErrors.stream().findFirst().orElse(firstError.get()));
                break;
            }
        }
    }

    @Override
    public String getMessage() {
        return firstError.get();
    }
}
