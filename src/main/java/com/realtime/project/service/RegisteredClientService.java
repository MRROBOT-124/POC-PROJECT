package com.realtime.project.service;

import com.realtime.project.constants.AuthenticationMethodEnum;
import com.realtime.project.constants.AuthorizationGrantTypeEnum;
import com.realtime.project.constants.HelperConstants;
import com.realtime.project.entity.AuthenticationMethod;
import com.realtime.project.entity.Client;
import com.realtime.project.entity.GrantType;
import com.realtime.project.entity.OutboxEntity;
import com.realtime.project.repository.ClientRepository;
import com.realtime.project.repository.OutboxEventRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REGISTER CLIENT DETAILS STORED IN POSTGRESQL DATABASE
 * THIS CLASS CONTAINS METHODS THAT CAN PULL THE NECESSARY RECORDS
 * NEEDED.
 */
@Slf4j
@Service
@Transactional
public class RegisteredClientService implements RegisteredClientRepository {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OutboxEventRepository outboxEventRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * PERSIST CLIENT DETAILS TO THE DATABASE
     * CHECKS IF THE CLIENT IS ALREADY PRESENT IN THE DATABASE
     * IF PRESENT UPDATE THE CLIENT INFO OR ELSE INSERTS A NEW RECORDS
     * @param registeredClient the {@link RegisteredClient}
     */
    @Override
    public void save(RegisteredClient registeredClient) {
        log.info("Entered RegisteredClientService ---> save() --> Attempting to persist client information");
        Client client = Client.builder().clientId(registeredClient.getClientId())
                        .clientSecret(registeredClient.getClientSecret())
                        .clientIdIssuedAt(registeredClient.getClientIdIssuedAt())
                        .clientSecretExpiresAt(registeredClient.getClientSecretExpiresAt())
                        .clientAuthenticationMethods(registeredClient.getClientAuthenticationMethods().stream()
                            .map(i -> {
                                if(i.getValue().equalsIgnoreCase(AuthenticationMethodEnum.CLIENT_SECRET_BASIC.toString())) {
                                     return AuthenticationMethod.builder().value(AuthenticationMethodEnum.CLIENT_SECRET_BASIC).build();
                                }
                                return null;

                            }).collect(Collectors.toSet()))
                .authorizationGrantTypes(registeredClient.getAuthorizationGrantTypes().stream()
                        .map(grant -> {
                            if(grant.getValue().equalsIgnoreCase(AuthorizationGrantTypeEnum.AUTHORIZATION_CODE.toString())) {
                                return GrantType.builder().value(AuthorizationGrantTypeEnum.AUTHORIZATION_CODE).build();
                            }
                            else if(grant.getValue().equalsIgnoreCase(AuthorizationGrantTypeEnum.CLIENT_CREDENTIALS.toString())) {
                                return GrantType.builder().value(AuthorizationGrantTypeEnum.CLIENT_CREDENTIALS).build();
                            }
                            return GrantType.builder().value(AuthorizationGrantTypeEnum.REFRESH_TOKEN).build();
                        }).collect(Collectors.toSet()))
                .scopes(String.join(",", registeredClient.getScopes()))
                .redirectUris(String.join(",", registeredClient.getRedirectUris()))
                .build();
        OutboxEntity outboxEntity = OutboxEntity.builder().aggregateid(client.getId() + "_" + client.getClientName())
                .type(HelperConstants.CLIENT)
                .aggregatetype(HelperConstants.CLIENT)
                .payload(OutboxEntity.convertJson(client)).build();
        clientRepository.save(client);
        outboxEventRepository.save(outboxEntity);
        log.info("Exited RegisteredClientService ---> save() ---> Successfully persisted data");
    }


    /**
     * PERSIST CLIENT DETAILS TO THE DATABASE
     * CHECKS IF THE CLIENT IS ALREADY PRESENT IN THE DATABASE
     * IF PRESENT UPDATE THE CLIENT INFO OR ELSE INSERTS A NEW RECORDS
     * @param client the {@link Client}
     */
    public Client save(Client client) {
        log.info("Entered RegisteredClientService ---> save() --> Attempting to persist client information");
        client.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
        OutboxEntity outboxEntity = OutboxEntity.builder().aggregateid(client.getId() + "_" + client.getClientName())
                .type(HelperConstants.CLIENT)
                .aggregatetype(HelperConstants.CLIENT)
                .payload(OutboxEntity.convertJson(client)).build();
        outboxEventRepository.save(outboxEntity);
        log.info("Exited RegisteredClientService ---> save() ---> Successfully persisted data");
        return clientRepository.save(client);
    }


    /**
     * FINDS THE CLIENT BY USING CUSTOM GENERATED ID (RANDOM NUMBER + TIMESTAMP)
     * AND RETURNS THE CLIENT FOR AUTHENTICATION
     * @param id the registration identifier
     */
    @Override
    public RegisteredClient findById(String id) {
        log.info("Entered RegisteredClientService ---> findById() ---> Attempting to find client details by using random generated id for the client");
        Optional<Client> optionalClient = clientRepository.findById(id);
        if(optionalClient.isEmpty()) {
            log.error("Client does not exists under the following id: {}", id);
            return null;
        }
        RegisteredClient.Builder builder = RegisteredClient.withId(optionalClient.get().getId());

        builder.clientId(optionalClient.get().getClientId())
                .clientName(optionalClient.get().getClientName())
                .clientSecret(optionalClient.get().getClientSecret());

        optionalClient.get().getClientAuthenticationMethods().forEach(auth ->
            builder.clientAuthenticationMethod(new ClientAuthenticationMethod(auth.getValue().name()))
        );
        optionalClient.get().getAuthorizationGrantTypes().forEach(grant ->
            builder.authorizationGrantType(new AuthorizationGrantType(grant.getValue().name()))
        );
        log.info("Exited RegisteredClientService ---> findById() ---> Client Found for the following id: {}", id);
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
     */
    @Override
    public RegisteredClient findByClientId(String clientId) {
        log.info("Entered RegisteredClientService ---> findById() ---> Attempting to find client details by using random generated id for the client");
        Optional<Client> optionalClient = clientRepository.findByClientId(clientId);
        if(optionalClient.isEmpty()) {
            log.error("Client does not exists under the following id: {}", clientId);
            return null;
        }
        RegisteredClient.Builder builder = RegisteredClient.withId(optionalClient.get().getId());
        builder.clientId(optionalClient.get().getClientId())
                .clientName(optionalClient.get().getClientName())
                .clientSecret(optionalClient.get().getClientSecret());

        optionalClient.get().getClientAuthenticationMethods().forEach(auth -> {
            if(auth.getValue().name().equalsIgnoreCase(AuthenticationMethodEnum.CLIENT_SECRET_BASIC.name())) {
                builder.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);
            }
        });
        optionalClient.get().getAuthorizationGrantTypes().forEach(grant -> {
            if(grant.getValue().name().equals(AuthorizationGrantTypeEnum.CLIENT_CREDENTIALS.name())) {
                builder.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS);
            }
            else if(grant.getValue().name().equals(AuthorizationGrantTypeEnum.AUTHORIZATION_CODE.name())) {
                builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
            }
            else if(grant.getValue().name().equals(AuthorizationGrantTypeEnum.REFRESH_TOKEN.name())) {
                builder.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN);
            }
        });
        log.info("Exited RegisteredClientService ---> findById() ---> Client Found for the following clientId: {}", clientId);
        return builder.redirectUri(optionalClient.get().getRedirectUris())
                .clientIdIssuedAt(optionalClient.get().getClientIdIssuedAt())
                .clientSecretExpiresAt(optionalClient.get().getClientSecretExpiresAt())
                .scope(optionalClient.get().getScopes())
                .build();
    }
}
