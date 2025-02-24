package com.sandbox.authify.infra.util;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.sandbox.authify.core.port.util.IdGenerator;
import org.springframework.stereotype.Component;

@Component
public class NanoidGenerator implements IdGenerator {
    private static final char[] DEFAULT_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    @Override
    public String generate() {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, DEFAULT_ALPHABET, NanoIdUtils.DEFAULT_SIZE);
    }

}
