package com.sandbox.authify.core.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity(name = "users")
@Setter
@Getter
public class User {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "username", unique = true, length = 36)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}