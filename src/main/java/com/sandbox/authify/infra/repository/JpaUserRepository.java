package com.sandbox.authify.infra.repository;

import com.sandbox.authify.core.domain.entity.User;
import com.sandbox.authify.core.port.repository.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User, String>, UserRepository {
}
