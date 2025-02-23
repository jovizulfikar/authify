package com.sandbox.authify.core.port.security;

import com.sandbox.authify.core.domain.jose.JsonWebSignature;
import com.sandbox.authify.core.domain.oauth2.JwtClaims;

import java.security.Key;

public interface JwtService {
    String sign(JsonWebSignature jws);
    JwtClaims verify(String jwt, Key jwsKey);
    JwtClaims getClaims(String jwt, Key jwsKey);
}
