package com.realtime.project.controller;

import com.realtime.project.entity.UserDetails;
import com.realtime.project.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * USER CONTROLLER THAT PROVIDES END POINTS USED TO REGISTER A NEW
 * USER OR GET CLIENT DETAILS
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * USED TO REGISTER A NEW USER
     * @param userDetails
     * @return
     */
    @PostMapping(value = "/add")
    public Mono<UserDetails> registerNewUser(@RequestBody UserDetails userDetails) {
        return Mono.just(userDetailsService.persistUser(userDetails));
    }

    /**
     * USED TO GET USER DETAILS
     * @param username
     * @return
     */
    @GetMapping(value = "/get")
    public Mono<org.springframework.security.core.userdetails.UserDetails> getUserInfo(@RequestParam String username) {
        return Mono.just(userDetailsService.loadUserByUsername(username));
    }
}
