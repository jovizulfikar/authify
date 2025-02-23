package com.sandbox.authify.core.application.usecase.revocation.provider;

import com.sandbox.authify.core.application.usecase.revocation.RevokeTokenRequest;
import com.sandbox.authify.core.application.usecase.revocation.RevokeTokenResponse;
import com.sandbox.authify.core.port.service.AccessTokenBlacklist;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RevokeAccessToken implements RevocationProvider {

    private final AccessTokenBlacklist accessTokenBlacklist;

    @Override
    public RevokeTokenResponse revoke(RevokeTokenRequest revokeTokenRequest) {
        accessTokenBlacklist.add(revokeTokenRequest.getToken());
        return RevokeTokenResponse.builder()
                .token(revokeTokenRequest.getToken())
                .build();
    }
}
