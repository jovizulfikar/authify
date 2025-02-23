package com.sandbox.authify.infra.config;

import com.sandbox.authify.core.common.config.OidcDiscoveryConfig;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("oidc-discovery")
@Setter
@Getter
public class AuthifyOidcDiscoveryConfig implements OidcDiscoveryConfig {
    private String jwksUri;
}
