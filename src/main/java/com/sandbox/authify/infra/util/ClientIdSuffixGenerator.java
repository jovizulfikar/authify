package com.sandbox.authify.infra.util;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;
import com.sandbox.authify.core.port.util.IdGenerator;
import org.springframework.stereotype.Component;

@Component
public class ClientIdSuffixGenerator implements IdGenerator {

    private static final char[] DEFAULT_ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    @Override
    public String generate() {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, DEFAULT_ALPHABET, 6);
    }

}
