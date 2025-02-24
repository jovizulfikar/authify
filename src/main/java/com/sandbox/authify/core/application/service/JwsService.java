package com.sandbox.authify.core.application.service;

import com.sandbox.authify.core.domain.entity.Client;
import com.sandbox.authify.core.domain.entity.User;
import com.sandbox.authify.core.domain.jose.JsonWebSignature;
import com.sandbox.authify.core.domain.jose.SignatureAlgorithm;
import com.sandbox.authify.core.domain.oauth2.JwtClaims;
import com.sandbox.authify.core.port.config.JwsConfig;
import com.sandbox.authify.core.port.config.JwtConfig;
import com.sandbox.authify.core.port.util.IdGenerator;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
@RequiredArgsConstructor
public class JwsService {
    
    private final JwtConfig jwtConfig;
    private final JwsConfig jwsConfig;
    private final IdGenerator idGenerator;
    private final KeyManager keyManager;

    public JsonWebSignature generateJws(Client client, User user, Set<String> scopes) {
        var userId = Optional.ofNullable(user).map(User::getId).orElse(null);
        var iat = System.currentTimeMillis();
        var exp = iat + TimeUnit.SECONDS.toMillis(client.getAccessTokenTtl());
        var jti = idGenerator.generate();
        var aud = client.getAudienceUris();

        var rsaPrivateKey = keyManager.getRsaPrivateKey();

        var claims = JwtClaims.builder()
                .iss(jwtConfig.getIssuer())
                .exp(exp)
                .iat(iat)
                .aud(aud)
                .sub(userId)
                .clientId(client.getClientId())
                .jti(jti)
                .scope(scopes)
                .build();

        return JsonWebSignature.builder()
                .claims(claims)
                .key(rsaPrivateKey)
                .algorithm(SignatureAlgorithm.RS256)
                .keyId(jwsConfig.getKeyId())
                .build();
    }

    public JsonWebSignature generateJws(Client client, Set<String> scopes) {
        return generateJws(client, null, scopes);
    }
}
