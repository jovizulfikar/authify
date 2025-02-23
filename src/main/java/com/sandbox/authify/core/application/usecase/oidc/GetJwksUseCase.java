package com.sandbox.authify.core.application.usecase.oidc;

import com.sandbox.authify.core.application.service.KeyManager;
import com.sandbox.authify.core.common.config.JwtConfig;
import com.sandbox.authify.core.common.util.Base64;
import com.sandbox.authify.core.domain.jose.JsonWebKey;
import com.sandbox.authify.core.domain.jose.JsonWebKeySet;
import com.sandbox.authify.core.domain.jose.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import java.util.Collections;

@RequiredArgsConstructor
public class GetJwksUseCase {

    private final KeyManager keyManager;
    private final JwtConfig jwtConfig;

    public JsonWebKeySet getJwks() {
        var publicKey = keyManager.getRsaPublicKey();

        var jwk = JsonWebKey.builder()
                .kty("RSA")
                .use("sig")
                .alg(SignatureAlgorithm.RS256.name())
                .kid(jwtConfig.getKeyId())
                .e(Base64.encode(publicKey.getPublicExponent().toByteArray()))
                .n(Base64.encode(keyManager.getPublicKeyMagnitude()))
                .build();

        return JsonWebKeySet.builder()
            .keys(Collections.singletonList(jwk))
            .build();
    }
}
