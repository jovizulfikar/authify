package com.sandbox.authify.core.domain.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthorizationGrantType {
    REFRESH_TOKEN("refresh_token"),
    CLIENT_CREDENTIALS("client_credentials"),
    PASSWORD("password");

    private final String granType;
}
