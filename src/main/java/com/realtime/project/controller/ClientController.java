package com.realtime.project.controller;


import com.realtime.project.constants.HelperConstants;
import com.realtime.project.entity.Client;
import com.realtime.project.exceptions.DataNotFoundException;
import com.realtime.project.resolvers.RegisteredClientResolvers;
import com.realtime.project.service.RegisteredClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * CLIENT CONTROLLER THAT PROVIDES END POINTS USED TO REGISTER A NEW
 * CLIENT OR GET CLIENT DETAILS
 */
@Slf4j
@RestController
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired
    private RegisteredClientService registeredClientService;
    @Autowired
    private RegisteredClientResolvers registeredClientResolvers;

    /**
     * USED TO REGISTER A NEW CLIENT
     * @param client "client"
     * @return "client"
     */
    @PostMapping(value = "/add")
    public Mono<Client> registerClient(@RequestBody Client client) {
        log.info("Entered ClientController ---> registerClient ---> Attempting to register new client");
        registeredClientService.save(client);
        log.info("Exited ClientController --> registerClient ---> Client Registered Successfully");
        return Mono.just(client);
    }

    @MutationMapping
    public Mono<Client> registerClientGraphQL(@Argument Client client) {
        log.info("Entered ClientController ---> registerClientGraphQL ---> Attempting to register new client");
        Client clientGraphQL = registeredClientResolvers.registerClientGraphQL(client);
        log.info("Exited ClientController --> registerClientGraphQL ---> Client Registered Successfully");
        return Mono.just(clientGraphQL);
    }


    /**
     * USED TO GET CLIENT DETAILS
     * @return "RegisteredClient"
     */
    @GetMapping("/get")
    public Mono<RegisteredClient> getClient(@RequestParam String clientID) throws DataNotFoundException {
        log.info("Entered ClientController ---> getClient ---> Fetching Client Details");
        RegisteredClient clientId = registeredClientService.findByClientId(clientID);
        if(Objects.isNull(clientId)) {
            throw new DataNotFoundException(HelperConstants.CLIENT_NOT_FOUND);
        }
        log.info("Exited ClientController ---> getClient ---> Client Details fetched");
        return Mono.just(clientId);
    }

    /**
     * USED TO GET CLIENT DETAILS
     * @return "RegisteredClient"
     */
    @QueryMapping
    public Mono<Client> findByClientIdGraphQL(@Argument String clientID) throws DataNotFoundException {
        log.info("Entered ClientController ---> findByClientIdGraphQL ---> Fetching Client Details");
        Client clientIdGraphQL = registeredClientResolvers.findByClientIdGraphQL(clientID);
        if(Objects.isNull(clientIdGraphQL)) {
            throw new DataNotFoundException(HelperConstants.CLIENT_NOT_FOUND);
        }
        log.info("Exited ClientController ---> findByClientIdGraphQL ---> Client Details fetched");
        return Mono.just(clientIdGraphQL);
    }

    /**
     * USED TO GET CLIENT DETAILS
     * @return "RegisteredClient"
     */
    @QueryMapping
    public Mono<Client> findByIdGraphQL(@Argument String id) throws DataNotFoundException {
        log.info("Entered ClientController ---> findByIdGraphQL ---> Fetching Client Details");
        Client clientIdGraphQL = registeredClientResolvers.findByIdGraphQL(id);
        if(Objects.isNull(clientIdGraphQL)) {
            throw new DataNotFoundException(HelperConstants.CLIENT_NOT_FOUND);
        }
        log.info("Exited ClientController ---> findByIdGraphQL ---> Client Details fetched");
        return Mono.just(clientIdGraphQL);
    }
}
