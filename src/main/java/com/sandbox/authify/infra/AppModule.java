package com.sandbox.authify.infra;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.sandbox.authify.core.application.service.ClientService;
import com.sandbox.authify.core.application.service.JwsService;
import com.sandbox.authify.core.application.service.KeyManager;
import com.sandbox.authify.core.application.service.RefreshTokenService;
import com.sandbox.authify.core.application.usecase.authentication.provider.AuthenticateClientCredentials;
import com.sandbox.authify.core.application.usecase.authentication.provider.AuthenticateRefreshToken;
import com.sandbox.authify.core.application.usecase.authentication.provider.AuthenticateResourceOwnerPasswordCredentials;
import com.sandbox.authify.core.application.usecase.authentication.provider.AuthenticationProviderFactory;
import com.sandbox.authify.core.application.usecase.client.RegisterClientUseCase;
import com.sandbox.authify.core.application.usecase.oidc.GetJwksUseCase;
import com.sandbox.authify.core.application.usecase.oidc.GetOidcDiscoveryUseCase;
import com.sandbox.authify.core.application.usecase.revocation.provider.RevocationProviderFactory;
import com.sandbox.authify.core.application.usecase.revocation.provider.RevokeAccessToken;
import com.sandbox.authify.core.application.usecase.revocation.provider.RevokeRefreshToken;
import com.sandbox.authify.core.application.usecase.user.RegisterUserUseCase;
import com.sandbox.authify.core.common.config.JwsConfig;
import com.sandbox.authify.core.common.config.JwtConfig;
import com.sandbox.authify.core.common.config.OidcDiscoveryConfig;
import com.sandbox.authify.core.port.repository.ClientRepository;
import com.sandbox.authify.core.port.repository.RefreshTokenRepository;
import com.sandbox.authify.core.port.repository.UserRepository;
import com.sandbox.authify.core.port.security.Hashing;
import com.sandbox.authify.core.port.security.JwtService;
import com.sandbox.authify.core.port.util.IdGenerator;
import com.sandbox.authify.core.port.util.PasswordGenerator;
import com.sandbox.authify.infra.config.AuthifyJwtConfig;
import com.sandbox.authify.infra.security.BcryptHash;
import com.sandbox.authify.infra.security.BitbucketJoseJwtService;
import com.sandbox.authify.infra.util.NanoIdGenerator;
import com.sandbox.authify.infra.util.PassayPasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@RequiredArgsConstructor
public class AppModule {

    @Bean
    public JwtConfig jwtConfig(AuthifyJwtConfig authifyJwtConfig) {
        return authifyJwtConfig;
    }

    @Bean
    public JwtService jwtService(BitbucketJoseJwtService bitbucketJoseJwtService) {
        return bitbucketJoseJwtService;
    }

    @Bean
    public PasswordGenerator passwordGenerator(PassayPasswordGenerator passayPasswordGenerator) {
        return passayPasswordGenerator;
    }

    @Bean
    public IdGenerator idGenerator(NanoIdGenerator nanoIdGenerator) {
        return nanoIdGenerator;
    }

    @Bean
    public Hashing hashing(BcryptHash bcryptHash) {
        return bcryptHash;
    }
    
    @Bean
    public RegisterClientUseCase registerClientUseCase(
            ClientRepository clientRepository,
            IdGenerator idGenerator,
            Hashing hashing,
            PasswordGenerator passwordGenerator
    ) {
        return new RegisterClientUseCase(clientRepository, idGenerator, hashing, passwordGenerator);
    }

    @Bean
    public ObjectMapper objectMapper() {
        var objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return objectMapper;
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(UserRepository userRepository, Hashing hashing) {
        return new RegisterUserUseCase(userRepository, hashing);
    }

    @Bean
    public AuthenticateClientCredentials clientCredentials(
            ClientService clientService,
            JwsService jwsService,
            JwtService jwtService,
            RefreshTokenService refreshTokenService
    ) {
        return new AuthenticateClientCredentials(jwsService, jwtService, refreshTokenService, clientService);
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public KeyManager keyManager(JwtConfig jwtConfig, JwsConfig jwsConfig) {
        return new KeyManager(jwtConfig, jwsConfig);
    }

    @Bean
    public JwsService jwsService(JwtConfig jwtConfig, JwsConfig jwsConfig, IdGenerator idGenerator, KeyManager keyManager) {
        return new JwsService(jwtConfig, jwsConfig, idGenerator, keyManager);
    }

    @Bean
    public RefreshTokenService refreshTokenService(IdGenerator idGenerator, RefreshTokenRepository refreshTokenRepository) {
        return new RefreshTokenService(idGenerator, refreshTokenRepository);
    }

    @Bean
    public AuthenticateRefreshToken refreshToken(
            ClientService clientService,
            Hashing hashing,
            RefreshTokenRepository refreshTokenRepository,
            JwsService jwsService,
            JwtService jwtService,
            RefreshTokenService refreshTokenService
    ) {
        return new AuthenticateRefreshToken(clientService, refreshTokenRepository, jwsService, jwtService, refreshTokenService);
    }

    @Bean
    public AuthenticationProviderFactory authenticationProviderFactory(
            AuthenticateResourceOwnerPasswordCredentials authenticateResourceOwnerPasswordCredentials,
            AuthenticateClientCredentials authenticateClientCredentials,
            AuthenticateRefreshToken authenticateRefreshToken
    ) {
        return new AuthenticationProviderFactory(authenticateResourceOwnerPasswordCredentials, authenticateClientCredentials, authenticateRefreshToken);
    }

    @Bean
    public AuthenticateResourceOwnerPasswordCredentials resourceOwnerPasswordCredentials(
            ClientService clientService,
            UserRepository userRepository,
            Hashing hashing,
            JwsService jwsService,
            JwtService jwtService,
            RefreshTokenService refreshTokenService
    ) {
        return new AuthenticateResourceOwnerPasswordCredentials(clientService, userRepository, hashing, jwsService, jwtService, refreshTokenService);
    }

    @Bean
    public GetJwksUseCase getJwksUseCase(KeyManager keyManager, JwsConfig jwsConfig) {
        return new GetJwksUseCase(keyManager, jwsConfig);
    }

    @Bean
    public GetOidcDiscoveryUseCase getOpenidConfigurationUseCase(OidcDiscoveryConfig oidcDiscoveryConfig) {
        return new GetOidcDiscoveryUseCase(oidcDiscoveryConfig);
    }

    @Bean
    public RevocationProviderFactory revocationProviderFactory(
            RevokeAccessToken revokeAccessToken,
            RevokeRefreshToken revokeRefreshToken)
    {
        return new RevocationProviderFactory(revokeAccessToken, revokeRefreshToken);
    }

    @Bean
    public RevokeRefreshToken revokeRefreshToken(RefreshTokenRepository refreshTokenRepository) {
        return new RevokeRefreshToken(refreshTokenRepository);
    }

    @Bean
    public RevokeAccessToken revokeAccessToken() {
        return new RevokeAccessToken();
    }

    @Bean
    public ClientService clientService(ClientRepository clientRepository, Hashing hashing) {
        return new ClientService(clientRepository, hashing);
    }
}
