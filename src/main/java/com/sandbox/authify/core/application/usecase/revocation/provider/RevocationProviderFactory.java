package com.sandbox.authify.core.application.usecase.revocation.provider;

import com.sandbox.authify.core.common.exception.AppException;
import com.sandbox.authify.core.domain.oauth2.TokenTypeHint;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@ApplicationScoped
@RequiredArgsConstructor
public class RevocationProviderFactory {

    private final RevokeAccessToken revokeAccessToken;
    private final RevokeRefreshToken revokeRefreshToken;

    public static final String ERROR_UNSUPPORTED_TOKEN_TYPE = "REVOCATION_PROVIDER_FACTORY.UNSUPPORTED_TOKEN_TYPE";

    @SneakyThrows
    public RevocationProvider getByTokenType(String tokenType) {
        if (TokenTypeHint.ACCESS_TOKEN.getType().equals(tokenType)) {
            return revokeAccessToken;
        } else if (TokenTypeHint.REFRESH_TOKEN.getType().equals(tokenType)) {
            return revokeRefreshToken;
        } else {
            throw AppException.build(ERROR_UNSUPPORTED_TOKEN_TYPE);
        }
    }
}
