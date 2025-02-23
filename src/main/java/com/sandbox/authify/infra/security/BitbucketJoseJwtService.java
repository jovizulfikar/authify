package com.sandbox.authify.infra.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandbox.authify.core.common.config.JwtConfig;
import com.sandbox.authify.core.domain.jose.JsonWebSignature;
import com.sandbox.authify.core.domain.jose.SignatureAlgorithm;
import com.sandbox.authify.core.domain.oauth2.JwtClaims;
import com.sandbox.authify.core.port.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class BitbucketJoseJwtService implements JwtService {

    private final JwtConfig jwtConfig;
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public String sign(JsonWebSignature jws) {

        var joseJws = new org.jose4j.jws.JsonWebSignature();
        var claims = objectMapper.writeValueAsString(jws.getClaims());

        joseJws.setPayload(claims);
        joseJws.setKey(jws.getKey());
        joseJws.setKeyIdHeaderValue(jws.getKeyId());
        joseJws.setAlgorithmHeaderValue(jws.getAlgorithm().name());
        return joseJws.getCompactSerialization();
    }

    @Override
    @SneakyThrows
    public JwtClaims verify(String jwt, Key jwsPublicKey) {
        var jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setVerificationKey(jwsPublicKey)
                .setExpectedAudience(jwtConfig.getIssuer())
                .setJwsAlgorithmConstraints(
                        AlgorithmConstraints.ConstraintType.PERMIT,
                        Arrays.stream(SignatureAlgorithm.values()).map(Enum::name)
                                .toArray(String[]::new))
                .build();

        var jwtClaims = jwtConsumer.processToClaims(jwt);
        return objectMapper.readValue(jwtClaims.getRawJson(), new TypeReference<>() {});
    }

    @Override
    @SneakyThrows
    public JwtClaims getClaims(String jwt, Key jwsPublicKey) {
        var jwtConsumer = new JwtConsumerBuilder()
                .setVerificationKey(jwsPublicKey)
                .build();

        var jwtClaims = jwtConsumer.processToClaims(jwt);
        return objectMapper.readValue(jwtClaims.getRawJson(), new TypeReference<>() {});
    }
}
