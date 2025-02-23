package com.sandbox.authify.core.common.config;

public interface JwtConfig {
    String getIssuer();
    String getPrivateKey();
    String getPublicKey();
    String getKeyId();
}
