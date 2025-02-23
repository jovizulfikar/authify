package com.sandbox.authify.core.port.repository;

import com.sandbox.authify.core.domain.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findByToken(String value);
    Optional<RefreshToken> findByTokenAndClientId(String value, String clientId);
    void deleteByToken(String value);
}
