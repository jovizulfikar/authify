package com.sandbox.authify.infra.config;

import com.sandbox.authify.core.common.config.JwsConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("jws")
@Setter
@Getter
public class AuthifyJwsConfig implements JwsConfig {
    private String privateKey;
    private String publicKey;
    private String keyId;
}
