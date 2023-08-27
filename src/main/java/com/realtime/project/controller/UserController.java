package com.realtime.project.controller;

import com.realtime.project.entity.UserInfo;
import com.realtime.project.resolvers.UserInfoResolvers;
import com.realtime.project.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * USER CONTROLLER THAT PROVIDES END POINTS USED TO REGISTER A NEW
 * USER OR GET CLIENT DETAILS
 */
@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserInfoService userDetailsService;
    @Autowired
    private UserInfoResolvers userInfoResolvers;

    /**
     * USED TO REGISTER A NEW USER
     * @param userDetails "USER DETAILS"
     * @return "USER INFO"
     */
    @PostMapping(value = "/add")
    public Mono<UserInfo> registerNewUser(@RequestBody UserInfo userDetails) {
        log.info("UserController ---> registerNewUser ---> Attempting to register new user");
        return Mono.just(userDetailsService.persistUser(userDetails));
    }

    /**
     * USED TO GET USER DETAILS
     * @param username "USERNAME"
     * @return "USER DETAILS"
     */
    @GetMapping(value = "/get")
    public Mono<org.springframework.security.core.userdetails.UserDetails> getUserInfo(@RequestParam String username) {
        log.info("UserController ---> getUserInfo ---> Fetching user details");
        return Mono.just(userDetailsService.loadUserByUsername(username));
    }

    @QueryMapping
    public Mono<UserInfo> getUserGraphQL(@Argument String username) {
        log.info("UserController ---> getUserGraphQL ---> Fetching user details");
        return Mono.just(userInfoResolvers.getUser(username));
    }

    @QueryMapping
    public Mono<List<UserInfo>> getAllUserGraphQL() {
        log.info("UserController ---> getAllUserGraphQL ---> Fetching user details");
        return Mono.just(userInfoResolvers.getAllUsers());
    }

    @MutationMapping
    public Mono<UserInfo> registerNewUserGraphQL(@Argument UserInfo userInfo) {
        log.info("UserController ---> registerNewUserGraphQL ---> Attempting to register new user");
        return Mono.just(userInfoResolvers.addUser(userInfo));
    }
}
