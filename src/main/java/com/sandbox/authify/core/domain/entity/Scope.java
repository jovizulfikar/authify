package com.sandbox.authify.core.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "scopes")
@Setter
@Getter
public class Scope {

    @Id
    @Column(name = "id", length = 36)
    private String id;

    @Column(name = "name", length = 50)
    private String name;
}

