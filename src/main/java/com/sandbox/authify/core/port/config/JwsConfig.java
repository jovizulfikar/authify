package com.sandbox.authify.core.port.config;

public interface JwsConfig {
    String getPrivateKey();
    String getPublicKey();
    String getKeyId();
}
