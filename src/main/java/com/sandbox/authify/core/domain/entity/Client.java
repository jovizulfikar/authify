package com.sandbox.authify.core.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Entity(name = "clients")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Client {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "client_id", unique = true, length = 36)
    private String clientId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "client_id")
    private Set<ClientSecret> secrets;

    @Column(name = "name", length = 50)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_grant_types", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "grant_type", length = 50)
    private Set<String> grantTypes;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "client_audience_uris", joinColumns = @JoinColumn(name = "client_id"))
    @Column(name = "audience_uri")
    private Set<String> audienceUris;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "clients_scopes",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "scope_id"))
    private Set<Scope> scopes;

    @Builder.Default
    @Column(name = "access_token_ttl")
    private Long accessTokenTtl = TimeUnit.HOURS.toSeconds(1);

    @Builder.Default
    @Column(name = "refresh_token_ttl")
    private Long refreshTokenTtl = TimeUnit.DAYS.toSeconds(30);

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @PrePersist
    protected void onCreate() {
        issuedAt = LocalDateTime.now();
    }
}

