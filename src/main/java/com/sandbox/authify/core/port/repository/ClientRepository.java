package com.sandbox.authify.core.port.repository;

import com.sandbox.authify.core.common.dto.Page;
import com.sandbox.authify.core.domain.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository {
    List<Client> findAll(Page page);
    Optional<Client> findByClientId(String clientId);
    Client save(Client client);
}
