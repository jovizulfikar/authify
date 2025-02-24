package com.sandbox.authify.core.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "client_secrets")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ClientSecret {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "client_id", length = 36)
    private String clientId;

    @Column(name = "secret")
    private String secret;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @PrePersist
    protected void onCreate() {
        issuedAt = LocalDateTime.now();
    }
}

