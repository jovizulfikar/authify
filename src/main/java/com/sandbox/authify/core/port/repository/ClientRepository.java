package com.sandbox.authify.core.port.repository;

import com.sandbox.authify.core.domain.entity.Client;

import java.util.Optional;

public interface ClientRepository {
    Optional<Client> findByClientId(String clientId);
    Client save(Client client);
}
