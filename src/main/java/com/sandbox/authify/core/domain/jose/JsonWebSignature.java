package com.sandbox.authify.core.domain.jose;

import com.sandbox.authify.core.domain.oauth2.JwtClaims;
import lombok.Builder;
import lombok.Getter;

import java.security.Key;

@Builder
@Getter
public class JsonWebSignature {
    private JwtClaims claims;
    private Key key;
    private String keyId;
    private SignatureAlgorithm algorithm;
}
