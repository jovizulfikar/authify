package com.sandbox.authify.core.port.repository;

import com.sandbox.authify.core.domain.entity.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    User save(User user);
}
