package com.sandbox.authify.core.application.usecase.oidc;

import com.sandbox.authify.core.port.config.OidcDiscoveryConfig;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.*;

@ApplicationScoped
@RequiredArgsConstructor
public class GetOidcDiscoveryUseCase {

    private final OidcDiscoveryConfig oidcDiscoveryConfig;

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private String jwksUri;
    }

    public Response getOidcDiscovery() {
        return Response.builder()
                .jwksUri(oidcDiscoveryConfig.getJwksUri())
                .build();
    }
}
