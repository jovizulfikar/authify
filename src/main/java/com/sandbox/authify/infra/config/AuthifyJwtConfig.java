package com.sandbox.authify.infra.config;

import com.sandbox.authify.core.common.config.JwtConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("sso")
@Setter
@Getter
public class AuthifyJwtConfig implements JwtConfig {
    private String issuer;
}
