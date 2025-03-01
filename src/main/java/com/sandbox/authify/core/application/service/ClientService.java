package com.sandbox.authify.core.application.service;

import com.sandbox.authify.core.common.exception.AppException;
import com.sandbox.authify.core.domain.entity.Client;
import com.sandbox.authify.core.port.repository.ClientRepository;
import com.sandbox.authify.core.port.security.Hashing;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@ApplicationScoped
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final Hashing passwordHash;

    public static final String ERROR_UNKNOWN_CLIENT = "CLIENT_SERVICE.AUTHENTICATE.CLIENT_UNKNOWN_CLIENT";
    public static final String ERROR_INVALID_CLIENT_SECRET = "CLIENT_SERVICE.AUTHENTICATE.INVALID_CLIENT_SECRET";

    @SneakyThrows
    public Client authenticate(String clientId, String clientSecret) {
        var client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> AppException.build(ERROR_UNKNOWN_CLIENT));

        if (client.getSecrets().stream()
                .noneMatch(secret -> passwordHash.verify(clientSecret, secret.getSecret()))) {
            throw AppException.build(ERROR_INVALID_CLIENT_SECRET);
        }

        return client;
    }
}
