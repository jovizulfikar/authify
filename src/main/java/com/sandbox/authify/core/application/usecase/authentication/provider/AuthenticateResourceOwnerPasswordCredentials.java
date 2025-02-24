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
import com.sandbox.authify.core.port.repository.UserRepository;
import com.sandbox.authify.core.port.security.Hashing;
import com.sandbox.authify.core.port.security.JwtService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
public class AuthenticateResourceOwnerPasswordCredentials implements AuthenticationProvider {

    private final ClientService clientService;
    private final UserRepository userRepository;
    private final Hashing passwordHash;
    private final JwsService jwsService;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    @Override
    @SneakyThrows
    public AccessTokenResponse authenticate(AccessTokenRequest accessTokenRequest) {
        var client = clientService.authenticate(accessTokenRequest.getClientId(), accessTokenRequest.getClientSecret());

        // verify client grants
        if (!client.getGrantTypes().contains(AuthorizationGrantType.PASSWORD.getGranType())) {
            throw AppException.build(ERROR_UNAUTHORIZED_AUTH_FLOW);
        }

        // verify user pass
        var user = userRepository.findByUsername(accessTokenRequest.getUsername())
                .orElseThrow(() -> AppException.build(ERROR_INVALID_RESOURCE_OWNER_CREDENTIALS));

        if (!passwordHash.verify(accessTokenRequest.getPassword(), user.getPassword())) {
            throw AppException.build(ERROR_INVALID_RESOURCE_OWNER_CREDENTIALS);
        }

        var scopes = client.getScopes().stream()
                .map(Scope::getName)
                .collect(Collectors.toSet());

        var jws = jwsService.generateJws(client, user, scopes);
        var accessToken = jwtService.sign(jws);
        var refreshToken = refreshTokenService.generateRefreshToken(client, user);

        return AccessTokenResponse.builder()
                .accessToken(accessToken)
                .expiresIn(client.getAccessTokenTtl())
                .tokenType(TokenType.BEARER.getType())
                .refreshToken(refreshToken.getToken())
                .build();
    }
}
