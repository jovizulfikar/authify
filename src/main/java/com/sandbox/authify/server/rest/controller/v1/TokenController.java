package com.sandbox.authify.server.rest.controller.v1;

import com.sandbox.authify.core.application.usecase.authentication.AccessTokenRequest;
import com.sandbox.authify.core.application.usecase.authentication.AccessTokenResponse;
import com.sandbox.authify.core.application.usecase.authentication.provider.AuthenticationProviderFactory;
import com.sandbox.authify.core.application.usecase.revocation.RevokeTokenRequest;
import com.sandbox.authify.core.application.usecase.revocation.RevokeTokenResponse;
import com.sandbox.authify.core.application.usecase.revocation.provider.RevocationProviderFactory;
import com.sandbox.authify.core.domain.entity.Client;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/token")
@RequiredArgsConstructor
public class TokenController {

    private final AuthenticationProviderFactory authenticationProviderFactory;
    private final RevocationProviderFactory revocationProviderFactory;
    
    @PostMapping
    public ResponseEntity<AccessTokenResponse> postToken(@RequestBody AccessTokenRequest accessTokenRequest) {
        var response = authenticationProviderFactory.getByGrantType(accessTokenRequest.getGrantType())
                .authenticate(accessTokenRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/revoke")
    public ResponseEntity<RevokeTokenResponse> revoke(
            HttpServletRequest httpServletRequest,
            @RequestBody RevokeTokenRequest revokeTokenRequest
    ) {
        var client = (Client) httpServletRequest.getAttribute("client");
        revokeTokenRequest.setClientId(client.getClientId());

        var revocationProvider = revocationProviderFactory.getByTokenType(revokeTokenRequest.getTokenTypeHint());
        var response = revocationProvider.revoke(revokeTokenRequest);
        return ResponseEntity.ok(response);
    }
}
