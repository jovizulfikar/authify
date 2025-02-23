package com.sandbox.authify.infra.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthifyCache {
    public static final String ACCESS_TOKEN_BLACKLIST = "ACCESS_TOKEN_BLACKLIST";
}
