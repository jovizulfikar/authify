package com.sandbox.authify.infra.repository;

import com.sandbox.authify.core.domain.entity.RefreshToken;
import com.sandbox.authify.core.port.repository.RefreshTokenRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaRefreshTokenRepository extends JpaRepository<RefreshToken, String>, RefreshTokenRepository {
}
