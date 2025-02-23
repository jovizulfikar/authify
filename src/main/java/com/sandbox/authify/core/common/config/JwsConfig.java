package com.sandbox.authify.core.common.config;

public interface JwsConfig {
    String getPrivateKey();
    String getPublicKey();
    String getKeyId();
}
