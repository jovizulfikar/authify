package com.sandbox.authify.core.domain.oauth2;

import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
public class JwtClaims {
    private String iss;
    private Long iat;
    private Long exp;
    private Set<String> aud;
    private String sub;
    private String clientId;
    private String jti;
    private Long authTime;
    private Byte acr;
    private String amr;

    @Builder.Default
    private Set<String> scope = new HashSet<>();
}
