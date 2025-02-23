package com.sandbox.authify.core.domain.oauth2;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
public class JwtClaims {
    private String iss;
    private Long iat;
    private Long exp;
    private Set<String> aud;
    private String sub;
    private String clientId;
    private String jti;
    private Set<String> scope = new HashSet<>();
    private Long authTime;
    private Byte acr;
    private String amr;
}
