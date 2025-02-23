package com.sandbox.authify.core.port.repository;

import com.sandbox.authify.core.domain.entity.Scope;

import java.util.Optional;

public interface ScopeRepository {
    Optional<Scope> findByName(String name);
    Scope save(Scope apiScope);
}
