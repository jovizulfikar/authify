package com.sandbox.authify.core.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Base64 {

    public static String encode(String src) {
        return java.util.Base64.getEncoder().encodeToString(src.getBytes(StandardCharsets.UTF_8));
    }

    public static String encode(byte[] src) {
        return java.util.Base64.getEncoder().encodeToString(src);
    }

    public static String decode(String src) {
        return new String(java.util.Base64.getDecoder().decode(src), StandardCharsets.UTF_8);
    }
}
