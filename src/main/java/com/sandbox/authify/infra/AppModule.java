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
import com.sandbox.authify.core.application.usecase.client.GetClientsUseCase;
import com.sandbox.authify.core.application.usecase.client.RegisterClientUseCase;
import com.sandbox.authify.core.application.usecase.oidc.GetJwksUseCase;
import com.sandbox.authify.core.application.usecase.oidc.GetOidcDiscoveryUseCase;
import com.sandbox.authify.core.application.usecase.revocation.provider.RevocationProviderFactory;
import com.sandbox.authify.core.application.usecase.revocation.provider.RevokeAccessToken;
import com.sandbox.authify.core.application.usecase.revocation.provider.RevokeRefreshToken;
import com.sandbox.authify.core.application.usecase.user.RegisterUserUseCase;
import com.sandbox.authify.core.port.config.JwsConfig;
import com.sandbox.authify.core.port.config.JwtConfig;
import com.sandbox.authify.core.port.config.OidcDiscoveryConfig;
import com.sandbox.authify.core.port.repository.ClientRepository;
import com.sandbox.authify.core.port.repository.RefreshTokenRepository;
import com.sandbox.authify.core.port.repository.UserRepository;
import com.sandbox.authify.core.port.security.Hashing;
import com.sandbox.authify.core.port.security.JwtService;
import com.sandbox.authify.core.port.service.AccessTokenBlacklist;
import com.sandbox.authify.core.port.util.IdGenerator;
import com.sandbox.authify.core.port.util.PasswordGenerator;
import com.sandbox.authify.infra.util.ClientIdSuffixGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class AppModule {

    @Bean
    public ClientService clientService(ClientRepository clientRepository, Hashing hashing) {
        return new ClientService(clientRepository, hashing);
    }

    @Bean
    public KeyManager keyManager(JwsConfig jwsConfig) {
        return new KeyManager(jwsConfig);
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
    public RegisterClientUseCase registerClientUseCase(
            ClientRepository clientRepository,
            IdGenerator idGenerator,
            ClientIdSuffixGenerator clientIdSuffixGenerator,
            Hashing hashing,
            PasswordGenerator passwordGenerator
    ) {
        return new RegisterClientUseCase(clientRepository, idGenerator, clientIdSuffixGenerator, hashing,
                passwordGenerator);
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
    public JwsService jwsService(JwtConfig jwtConfig, JwsConfig jwsConfig, IdGenerator idGenerator, KeyManager keyManager) {
        return new JwsService(jwtConfig, jwsConfig, idGenerator, keyManager);
    }

    @Bean
    public RefreshTokenService refreshTokenService(IdGenerator idGenerator, RefreshTokenRepository refreshTokenRepository) {
        return new RefreshTokenService(idGenerator, refreshTokenRepository);
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
    public AuthenticateRefreshToken refreshToken(
            ClientService clientService,
            RefreshTokenRepository refreshTokenRepository,
            JwsService jwsService,
            JwtService jwtService,
            RefreshTokenService refreshTokenService
    ) {
        return new AuthenticateRefreshToken(clientService, refreshTokenRepository, jwsService, jwtService, refreshTokenService);
    }

    @Bean
    public RevocationProviderFactory revocationProviderFactory(
            RevokeAccessToken revokeAccessToken,
            RevokeRefreshToken revokeRefreshToken)
    {
        return new RevocationProviderFactory(revokeAccessToken, revokeRefreshToken);
    }

    @Bean
    public RevokeAccessToken revokeAccessToken(AccessTokenBlacklist accessTokenBlacklist) {
        return new RevokeAccessToken(accessTokenBlacklist);
    }

    @Bean
    public RevokeRefreshToken revokeRefreshToken(RefreshTokenRepository refreshTokenRepository) {
        return new RevokeRefreshToken(refreshTokenRepository);
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(UserRepository userRepository, Hashing hashing) {
        return new RegisterUserUseCase(userRepository, hashing);
    }

    @Bean
    public GetClientsUseCase getClientsUseCase(ClientRepository clientRepository) {
        return new GetClientsUseCase(clientRepository);
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
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
