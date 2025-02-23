package com.sandbox.authify.core.domain.jose;

import com.sandbox.authify.core.domain.oauth2.JwtClaims;
import lombok.Getter;
import lombok.Setter;

import java.security.Key;

@Setter
@Getter
public class JsonWebSignature {
    private JwtClaims claims;
    private Key key;
    private String keyId;
    private SignatureAlgorithm algorithm;
}
