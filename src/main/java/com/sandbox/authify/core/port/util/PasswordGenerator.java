package com.sandbox.authify.core.port.util;

public interface PasswordGenerator {
    String generate();
    String generate(Integer length);
}
