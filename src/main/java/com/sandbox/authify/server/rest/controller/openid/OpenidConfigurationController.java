package com.sandbox.authify.server.rest.controller.openid;

import com.sandbox.authify.core.application.usecase.oidc.GetJwksUseCase;
import com.sandbox.authify.core.application.usecase.oidc.GetOidcDiscoveryUseCase;
import com.sandbox.authify.core.domain.jose.JsonWebKeySet;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/.well-known/openid-configuration")
@RequiredArgsConstructor
public class OpenidConfigurationController {

    private final GetJwksUseCase getJwksUseCase;
    private final GetOidcDiscoveryUseCase getOidcDiscoveryUseCase;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @SneakyThrows
    public ResponseEntity<GetOidcDiscoveryUseCase.Response> getOpenidConfiguration() {
        var response = getOidcDiscoveryUseCase.getOidcDiscovery();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/jwks")
    public ResponseEntity<JsonWebKeySet> getJwks() {
        var response = getJwksUseCase.getJwks();
        return ResponseEntity.ok(response);
    }

}
