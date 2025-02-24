package com.sandbox.authify.core.application.service;

import com.sandbox.authify.core.domain.entity.Client;
import com.sandbox.authify.core.domain.entity.RefreshToken;
import com.sandbox.authify.core.domain.entity.User;
import com.sandbox.authify.core.port.repository.RefreshTokenRepository;
import com.sandbox.authify.core.port.util.IdGenerator;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
@RequiredArgsConstructor
public class RefreshTokenService {

    private final IdGenerator idGenerator;
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(Client client, User user) {
        user = Objects.nonNull(user) ? user : User.builder().build();
        var issuedAt = System.currentTimeMillis();
        var refreshTokenTtl = TimeUnit.SECONDS.toMillis(client.getRefreshTokenTtl());
        var expiredAt = LocalDateTime.ofInstant(Instant.ofEpochMilli(issuedAt + refreshTokenTtl), ZoneId.systemDefault());

        var refreshToken = RefreshToken.builder()
                .id(idGenerator.generate())
                .token(idGenerator.generate())
                .expiredAt(expiredAt)
                .userId(user.getId())
                .clientId(client.getId())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken generateRefreshToken(Client client) {
        return generateRefreshToken(client, null);
    }
}
