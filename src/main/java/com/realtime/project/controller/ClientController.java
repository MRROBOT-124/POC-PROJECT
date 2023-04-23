package com.realtime.project.controller;

import com.realtime.project.entity.Client;
import com.realtime.project.service.RegisteredClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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

    /**
     * USED TO REGISTER A NEW CLIENT
     * @param client
     * @return
     */
    @PostMapping(value = "/add")
    public Mono<Client> registerClient(@RequestBody Client client) {
        log.info("Entered ClientController ---> registerClient ---> Attempting to register new client");
        registeredClientService.save(client);
        log.info("Exited ClientController --> registerClient ---> Client Registered Successfully");
        return Mono.just(client);
    }


    /**
     * USED TO GET CLIENT DETAILS
     * @return
     */
    @GetMapping("/get")
    public Mono<RegisteredClient> getClient(@RequestParam String clientID) {
        log.info("Entered ClientController ---> getClient ---> Fetching Client Details");
        RegisteredClient clientId = registeredClientService.findByClientId(clientID);
        log.info("Exited ClientController ---> getClient ---> Client Details fetched");
        return Mono.just(clientId);

    }
}
