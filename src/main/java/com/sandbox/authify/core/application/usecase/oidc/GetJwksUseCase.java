package com.sandbox.authify.core.application.usecase.oidc;

import com.sandbox.authify.core.application.service.KeyManager;
import com.sandbox.authify.core.common.util.Base64;
import com.sandbox.authify.core.domain.jose.JsonWebKey;
import com.sandbox.authify.core.domain.jose.JsonWebKeySet;
import com.sandbox.authify.core.domain.jose.SignatureAlgorithm;
import com.sandbox.authify.core.port.config.JwsConfig;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.Collections;

@ApplicationScoped
@RequiredArgsConstructor
public class GetJwksUseCase {

    private final KeyManager keyManager;
    private final JwsConfig jwsConfig;

    public JsonWebKeySet getJwks() {
        var publicKey = keyManager.getRsaPublicKey();

        var jwk = JsonWebKey.builder()
                .kty("RSA")
                .use("sig")
                .alg(SignatureAlgorithm.RS256.name())
                .kid(jwsConfig.getKeyId())
                .e(Base64.encode(publicKey.getPublicExponent().toByteArray()))
                .n(Base64.encode(keyManager.getPublicKeyMagnitude()))
                .build();

        return JsonWebKeySet.builder()
            .keys(Collections.singletonList(jwk))
            .build();
    }
}
