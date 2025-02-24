package com.sandbox.authify.infra.repository;

import com.sandbox.authify.core.common.dto.Page;
import com.sandbox.authify.core.domain.entity.Client;
import com.sandbox.authify.core.port.repository.ClientRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaClientRepository extends JpaRepository<Client, String>, ClientRepository {

    default List<Client> findAll(Page page) {
        var pageable = PageRequest.of(page.getNumber() - 1, page.getSize());
        return findAll(pageable).getContent();
    }
}
