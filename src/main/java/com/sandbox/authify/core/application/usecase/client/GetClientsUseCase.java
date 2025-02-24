package com.sandbox.authify.core.application.usecase.client;

import com.sandbox.authify.core.common.dto.Page;
import com.sandbox.authify.core.port.repository.ClientRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class GetClientsUseCase {

    private final ClientRepository clientRepository;

    @Builder
    @Getter
    public static class Request {

        @Builder.Default
        private Page page = Page.of(1, 25);
    }

    @Builder
    @Getter
    public static class Response {

        @Builder.Default
        List<Client> clients = new ArrayList<>();

        @Builder
        @Getter
        public static class Client {
            private String id;
            private String clientId;
            private String name;
        }
    }

    public Response getClients(Request request) {
        var clients = clientRepository.findAll(request.getPage()).stream()
                .map(client -> Response.Client.builder()
                        .id(client.getId())
                        .clientId(client.getClientId())
                        .name(client.getName())
                        .build())
                .toList();

        return Response.builder()
                .clients(clients)
                .build();
    }
}
