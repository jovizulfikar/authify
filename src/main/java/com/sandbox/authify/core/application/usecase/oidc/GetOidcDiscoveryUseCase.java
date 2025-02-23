package com.sandbox.authify.core.application.usecase.oidc;

import com.sandbox.authify.core.common.config.OidcDiscoveryConfig;
import lombok.*;

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
