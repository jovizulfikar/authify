package com.sandbox.authify.server.runner;

import com.sandbox.authify.core.application.usecase.client.GetClientsUseCase;
import com.sandbox.authify.core.application.usecase.client.RegisterClientUseCase;
import com.sandbox.authify.core.common.dto.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class RegisterInitialClient implements ApplicationRunner {

    private final GetClientsUseCase getClientsUseCase;
    private final RegisterClientUseCase registerClientUseCase;

    @Override
    public void run(ApplicationArguments args) {
        CompletableFuture.runAsync(() -> {
            try {
                registerInitialClient();
            } catch (Exception e) {
                log.error("REGISTER-INITIAL-CLIENT - error={}", e.getMessage(), e);
            }
        });
    }

    private void registerInitialClient() {
        var getClientsRequest = GetClientsUseCase.Request.builder()
                .page(Page.of(1, 1))
                .build();

        var getClientsResponse = getClientsUseCase.getClients(getClientsRequest);

        if (!getClientsResponse.getClients().isEmpty()) {
            return;
        }

        var registerClientRequest = RegisterClientUseCase.Request.builder()
                .clientId("authify")
                .clientName("Authify")
                .build();

        var registerClientResponse = registerClientUseCase.registerClient(registerClientRequest);
        log.info("REGISTER-INITIAL-CLIENT - client={}", registerClientResponse);
    }
}
