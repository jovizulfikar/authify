package com.sandbox.authify.core.application.usecase.revocation;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RevokeTokenResponse {
    private String token;
}
