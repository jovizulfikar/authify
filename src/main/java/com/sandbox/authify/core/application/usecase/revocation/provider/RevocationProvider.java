package com.sandbox.authify.core.application.usecase.revocation.provider;

import com.sandbox.authify.core.application.usecase.revocation.RevokeTokenRequest;
import com.sandbox.authify.core.application.usecase.revocation.RevokeTokenResponse;

public interface RevocationProvider {
    RevokeTokenResponse revoke(RevokeTokenRequest revokeTokenRequest);
}
