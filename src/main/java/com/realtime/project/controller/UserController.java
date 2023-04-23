package com.realtime.project.controller;

import com.realtime.project.entity.UserInfo;
import com.realtime.project.service.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * USER CONTROLLER THAT PROVIDES END POINTS USED TO REGISTER A NEW
 * USER OR GET CLIENT DETAILS
 */
@Slf4j
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
    public Mono<UserInfo> registerNewUser(@RequestBody UserInfo userDetails) {
        log.info("UserController ---> registerNewUser ---> Attempting to register new user");
        return Mono.just(userDetailsService.persistUser(userDetails));
    }

    /**
     * USED TO GET USER DETAILS
     * @param username
     * @return
     */
    @GetMapping(value = "/get")
    public Mono<org.springframework.security.core.userdetails.UserDetails> getUserInfo(@RequestParam String username) {
        log.info("UserController ---> getUserInfo ---> Fetching user details");
        return Mono.just(userDetailsService.loadUserByUsername(username));
    }
}
