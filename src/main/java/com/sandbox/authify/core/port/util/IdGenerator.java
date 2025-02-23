package com.sandbox.authify.core.port.util;

public interface IdGenerator {
    String generate();
    String generate(Integer length);
}
