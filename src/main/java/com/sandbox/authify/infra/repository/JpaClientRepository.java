package com.sandbox.authify.infra.repository;

import com.sandbox.authify.core.domain.entity.Client;
import com.sandbox.authify.core.port.repository.ClientRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaClientRepository extends JpaRepository<Client, String>, ClientRepository {
}
