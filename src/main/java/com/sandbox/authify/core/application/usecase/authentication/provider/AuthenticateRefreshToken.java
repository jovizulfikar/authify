package com.sandbox.authify.core.application.usecase.authentication.provider;

import com.sandbox.authify.core.application.service.ClientService;
import com.sandbox.authify.core.application.service.JwsService;
import com.sandbox.authify.core.application.service.RefreshTokenService;
import com.sandbox.authify.core.application.usecase.authentication.AccessTokenRequest;
import com.sandbox.authify.core.application.usecase.authentication.AccessTokenResponse;
import com.sandbox.authify.core.common.exception.AppException;
import com.sandbox.authify.core.domain.entity.Scope;
import com.sandbox.authify.core.domain.oauth2.AuthorizationGrantType;
import com.sandbox.authify.core.domain.oauth2.TokenType;
import com.sandbox.authify.core.port.repository.RefreshTokenRepository;
import com.sandbox.authify.core.port.security.JwtService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class AuthenticateRefreshToken implements AuthenticationProvider {

    private final ClientService clientService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwsService jwsService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Override
    @SneakyThrows
    public AccessTokenResponse authenticate(AccessTokenRequest accessTokenRequest) {
        var client = clientService.authenticate(accessTokenRequest.getClientId(), accessTokenRequest.getClientSecret());

        // verify client grants
        if (!client.getGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN.getGranType())) {
            throw AppException.build(ERROR_UNAUTHORIZED_AUTH_FLOW);
        }

        // verify refresh token
        var refreshToken = refreshTokenRepository.findByTokenAndClientId(accessTokenRequest.getRefreshToken(), client.getId())
                .orElseThrow(() -> AppException.build(ERROR_INVALID_REFRESH_TOKEN));

        if (refreshToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.deleteByToken(accessTokenRequest.getRefreshToken());
            throw AppException.build(ERROR_INVALID_REFRESH_TOKEN);
        }
        
        // generate access token
        var scopes = client.getScopes().stream()
                .map(Scope::getName)
                .collect(Collectors.toSet());

        var jws = jwsService.generateJws(client, scopes);
        var accessToken = jwtService.sign(jws);
        var newRefreshToken = refreshTokenService.generateRefreshToken(client);

        // delete used refresh token
        refreshTokenRepository.deleteByToken(accessTokenRequest.getRefreshToken());

        return AccessTokenResponse.builder()
                .accessToken(accessToken)
                .expiresIn(client.getAccessTokenTtl())
                .tokenType(TokenType.BEARER.getType())
                .refreshToken(newRefreshToken.getToken())
                .build();
    }
    

}
