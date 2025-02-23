package com.sandbox.authify.core.application.usecase.revocation.provider;

import com.sandbox.authify.core.application.usecase.revocation.RevokeTokenRequest;
import com.sandbox.authify.core.application.usecase.revocation.RevokeTokenResponse;
import com.sandbox.authify.core.port.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class RevokeRefreshToken implements RevocationProvider {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    @SneakyThrows
    public RevokeTokenResponse revoke(RevokeTokenRequest revokeTokenRequest) {
        refreshTokenRepository.findByTokenAndClientId(revokeTokenRequest.getToken(), revokeTokenRequest.getClientId())
                .ifPresent(refreshToken -> refreshTokenRepository.deleteByToken(refreshToken.getToken()));

        return RevokeTokenResponse.builder()
                .token(revokeTokenRequest.getToken())
                .build();
    }
}
