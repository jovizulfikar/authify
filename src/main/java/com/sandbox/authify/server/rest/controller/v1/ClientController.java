package com.sandbox.authify.server.rest.controller.v1;

import com.sandbox.authify.core.application.usecase.client.RegisterClientUseCase;
import com.sandbox.authify.server.rest.annotation.ApiScope;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final RegisterClientUseCase registerClientUseCase;

    @PostMapping
    @ApiScope("client:register")
    public ResponseEntity<RegisterClientUseCase.Response> postClients(@RequestBody RegisterClientUseCase.Request request) {
        var response = registerClientUseCase.registerClient(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }
}
