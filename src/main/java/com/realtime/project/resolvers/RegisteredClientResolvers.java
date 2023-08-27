package com.realtime.project.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.realtime.project.constants.AuthenticationMethodEnum;
import com.realtime.project.constants.AuthorizationGrantTypeEnum;
import com.realtime.project.entity.AuthenticationMethod;
import com.realtime.project.entity.Client;
import com.realtime.project.entity.GrantType;
import com.realtime.project.service.RegisteredClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RegisteredClientResolvers implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private RegisteredClientService registeredClientService;

    /**
     * PERSIST CLIENT DETAILS TO THE DATABASE
     * CHECKS IF THE CLIENT IS ALREADY PRESENT IN THE DATABASE
     * IF PRESENT UPDATE THE CLIENT INFO OR ELSE INSERTS A NEW RECORDS
     * @param client the {@link Client}
     */
    public Client registerClientGraphQL(Client client) {
        log.info("Entered RegisteredClientResolvers ---> registerClientGraphQL() --> Attempting to persist client information");
        return registeredClientService.save(client);
    }

    /**
     * FINDS THE CLIENT BY USING CUSTOM GENERATED ID (RANDOM NUMBER + TIMESTAMP)
     * AND RETURNS THE CLIENT FOR AUTHENTICATION
     * @param id the registration identifier
     */
    public Client findByIdGraphQL(String id) {
        log.info("Entered RegisteredClientResolvers ---> findByIdGraphQL() ---> Attempting to find client details by using random generated id for the client");
        RegisteredClient registeredClient = registeredClientService.findById(id);
        if(Objects.isNull(registeredClient)) {
            log.error("Client does not exists under the following id: {}", id);
            return null;
        }
        log.info("Exited RegisteredClientResolvers ---> findByIdGraphQL() ---> Client Found for the following id: {}", id);
        return Client.builder().clientId(registeredClient.getClientId())
                .clientName(registeredClient.getClientName())
                .clientSecret(registeredClient.getClientSecret())
                .clientAuthenticationMethods(registeredClient.getClientAuthenticationMethods().stream().map(auth -> {
                    if(auth.getValue().equalsIgnoreCase(AuthenticationMethodEnum.CLIENT_SECRET_BASIC.name())) {
                        return AuthenticationMethod.builder().value(AuthenticationMethodEnum.CLIENT_SECRET_BASIC).build();
                    }
                    return null;
                }).collect(Collectors.toSet()))
                .clientIdIssuedAt(registeredClient.getClientIdIssuedAt())
                .redirectUris(String.join(",", registeredClient.getRedirectUris()))
                .authorizationGrantTypes(registeredClient.getAuthorizationGrantTypes().stream().map(grant -> {
                    if(grant.getValue().equalsIgnoreCase(AuthorizationGrantTypeEnum.AUTHORIZATION_CODE.name())) {
                        return GrantType.builder().value(AuthorizationGrantTypeEnum.AUTHORIZATION_CODE).build();
                    }
                    else if(grant.getValue().equalsIgnoreCase(AuthorizationGrantTypeEnum.CLIENT_CREDENTIALS.name())) {
                        return GrantType.builder().value(AuthorizationGrantTypeEnum.CLIENT_CREDENTIALS).build();
                    }
                    return GrantType.builder().value(AuthorizationGrantTypeEnum.REFRESH_TOKEN).build();
                }).collect(Collectors.toSet()))
                .build();
    }


    /**
     * FINDS THE CLIENT BY USING CLIENT ID AND RETURNS
     * THE CLIENT FOR AUTHENTICATION
     * @param clientId the client identifier
     */
    public Client findByClientIdGraphQL(String clientId) {
        log.info("Entered RegisteredClientResolvers ---> findByClientIdGraphQL() ---> Attempting to find client details by using random generated id for the client");
        RegisteredClient registeredClient = registeredClientService.findByClientId(clientId);
        if(Objects.isNull(registeredClient)) {
            log.error("Client does not exists under the following id: {}", clientId);
            return null;
        }
        log.info("Exited RegisteredClientResolvers ---> findByClientIdGraphQL() ---> Client Found for the following clientId: {}", clientId);
        return Client.builder().clientId(registeredClient.getClientId())
                .clientName(registeredClient.getClientName())
                .clientSecret(registeredClient.getClientSecret())
                .clientAuthenticationMethods(registeredClient.getClientAuthenticationMethods().stream().map(auth -> {
                    if(auth.getValue().equalsIgnoreCase(AuthenticationMethodEnum.CLIENT_SECRET_BASIC.name())) {
                        return AuthenticationMethod.builder().value(AuthenticationMethodEnum.CLIENT_SECRET_BASIC).build();
                    }
                    return null;
                }).collect(Collectors.toSet()))
                .clientIdIssuedAt(registeredClient.getClientIdIssuedAt())
                .redirectUris(String.join(",", registeredClient.getRedirectUris()))
                .authorizationGrantTypes(registeredClient.getAuthorizationGrantTypes().stream().map(grant -> {
                    if(grant.getValue().equalsIgnoreCase(AuthorizationGrantTypeEnum.AUTHORIZATION_CODE.name())) {
                        return GrantType.builder().value(AuthorizationGrantTypeEnum.AUTHORIZATION_CODE).build();
                    }
                    else if(grant.getValue().equalsIgnoreCase(AuthorizationGrantTypeEnum.CLIENT_CREDENTIALS.name())) {
                        return GrantType.builder().value(AuthorizationGrantTypeEnum.CLIENT_CREDENTIALS).build();
                    }
                    return GrantType.builder().value(AuthorizationGrantTypeEnum.REFRESH_TOKEN).build();
                }).collect(Collectors.toSet()))
                .build();
    }

}
