package com.sandbox.authify.infra.repository;

import com.sandbox.authify.core.domain.entity.Scope;
import com.sandbox.authify.core.port.repository.ScopeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaScopeRepository extends JpaRepository<Scope, String>, ScopeRepository {
}
