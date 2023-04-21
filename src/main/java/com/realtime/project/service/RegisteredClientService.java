package com.realtime.project.service;

import com.realtime.project.entity.AuthenticationMethod;
import com.realtime.project.entity.Client;
import com.realtime.project.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * REGISTER CLIENT DETAILS STORED IN POSTGRESQL DATABASE
 * THIS CLASS CONTAINS METHODS THAT CAN PULL THE NECESSARY RECORDS
 * NEEDED.
 */
@Service
public class RegisteredClientService implements RegisteredClientRepository {

    @Autowired
    private ClientRepository clientRepository;

    /**
     * PERSIST CLIENT DETAILS TO THE DATABASE
     * CHECKS IF THE CLIENT IS ALREADY PRESENT IN THE DATABASE
     * IF PRESENT UPDATE THE CLIENT INFO OR ELSE INSERTS A NEW RECORDS
     * @param registeredClient the {@link RegisteredClient}
     */
    @Override
    public void save(RegisteredClient registeredClient) {
        clientRepository.save(new Client(registeredClient));
    }


    /**
     * PERSIST CLIENT DETAILS TO THE DATABASE
     * CHECKS IF THE CLIENT IS ALREADY PRESENT IN THE DATABASE
     * IF PRESENT UPDATE THE CLIENT INFO OR ELSE INSERTS A NEW RECORDS
     * @param client the {@link Client}
     */
    public Client save(Client client) {
        return clientRepository.save(client);
    }


    /**
     * FINDS THE CLIENT BY USING CUSTOM GENERATED ID (RANDOM NUMBER + TIMESTAMP)
     * AND RETURNS THE CLIENT FOR AUTHENTICATION
     * @param id the registration identifier
     * @return
     */
    @Override
    public RegisteredClient findById(String id) {
        Optional<Client> optionalClient = clientRepository.findById(id);
        RegisteredClient.Builder builder = RegisteredClient.withId(optionalClient.get().getId());

        builder.clientId(optionalClient.get().getClientId())
                .clientName(optionalClient.get().getClientName())
                .clientSecret(optionalClient.get().getClientSecret());

        optionalClient.get().getClientAuthenticationMethods().forEach(auth -> {
            builder.clientAuthenticationMethod(new ClientAuthenticationMethod(auth.getValue().toString()));
        });
        optionalClient.get().getAuthorizationGrantTypes().forEach(grant -> {
            builder.authorizationGrantType(new AuthorizationGrantType(grant.getValue().toString()));
        });
        return builder.redirectUri(optionalClient.get().getRedirectUris())
                .clientIdIssuedAt(optionalClient.get().getClientIdIssuedAt())
                .clientSecretExpiresAt(optionalClient.get().getClientSecretExpiresAt())
                .scope(optionalClient.get().getScopes())
                .build();
    }

    /**
     * FINDS THE CLIENT BY USING CLIENT ID AND RETURNS
     * THE CLIENT FOR AUTHENTICATION
     * @param clientId the client identifier
     * @return
     */
    @Override
    public RegisteredClient findByClientId(String clientId) {
        Optional<Client> optionalClient = clientRepository.findByClientId(clientId);
        RegisteredClient.Builder builder = RegisteredClient.withId(optionalClient.get().getId());
        builder.clientId(optionalClient.get().getClientId())
                .clientName(optionalClient.get().getClientName())
                .clientSecret(optionalClient.get().getClientSecret());

        optionalClient.get().getClientAuthenticationMethods().forEach(auth -> {
            builder.clientAuthenticationMethod(new ClientAuthenticationMethod(auth.getValue().toString()));
        });
        optionalClient.get().getAuthorizationGrantTypes().forEach(grant -> {
            builder.authorizationGrantType(new AuthorizationGrantType(grant.getValue().toString()));
        });
        return builder.redirectUri(optionalClient.get().getRedirectUris())
                .clientIdIssuedAt(optionalClient.get().getClientIdIssuedAt())
                .clientSecretExpiresAt(optionalClient.get().getClientSecretExpiresAt())
                .scope(optionalClient.get().getScopes())
                .build();
    }
}
