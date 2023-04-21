package com.realtime.project.controller;

import com.realtime.project.entity.Client;
import com.realtime.project.service.RegisteredClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * CLIENT CONTROLLER THAT PROVIDES END POINTS USED TO REGISTER A NEW
 * CLIENT OR GET CLIENT DETAILS
 */
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
        registeredClientService.save(client);
        return Mono.just(client);
    }


    /**
     * USED TO GET CLIENT DETAILS
     * @return
     */
    @GetMapping("/get")
    public Mono<RegisteredClient> getClient(@RequestParam String clientID) {
        RegisteredClient clientId = registeredClientService.findByClientId(clientID);
        return Mono.just(clientId);

    }
}
