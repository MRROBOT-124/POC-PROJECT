package com.realtime.project.service;

import com.realtime.project.constants.AuthenticationMethodEnum;
import com.realtime.project.constants.AuthorizationGrantTypeEnum;
import com.realtime.project.entity.AuthenticationMethod;
import com.realtime.project.entity.Client;
import com.realtime.project.entity.GrantType;
import com.realtime.project.repository.ClientRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RegisteredClientServiceTest {

    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private RegisteredClientService registeredClientService;

    @Test
    void testSave() {
        Mockito.when(clientRepository.save(any(Client.class))).thenReturn(new Client());
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("mock");
        registeredClientService.save(RegisteredClient.withId("mock").clientId("mock")
                        .clientSecret("mock")
                        .clientName("mock")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .redirectUri("mock").scope("mock").build());
        Mockito.verify(clientRepository, Mockito.atMostOnce()).save(any(Client.class));
        Mockito.verify(passwordEncoder, Mockito.atMostOnce()).encode(anyString());
    }

    @Test
    void testSaveWithClientParameter() {
        Mockito.when(clientRepository.save(any(Client.class))).thenReturn(new Client());
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("mock");
        registeredClientService.save(Client.builder().id("mock").clientId("mock")
                .clientSecret("mock")
                .clientName("mock")
                .authorizationGrantTypes(new HashSet<>(Arrays.asList(GrantType.builder().value(AuthorizationGrantTypeEnum.authorization_code).build())))
                .clientAuthenticationMethods(new HashSet<>(Arrays.asList(AuthenticationMethod.builder().value(AuthenticationMethodEnum.client_secret_basic).build())))
                .redirectUris("mock").scopes("mock").build());
        Mockito.verify(clientRepository, Mockito.atMostOnce()).save(any(Client.class));
        Mockito.verify(passwordEncoder, Mockito.atMostOnce()).encode(anyString());
    }

    @Test
    void testFindById() {
        Client client = Client.builder().id("mock").clientId("mock")
                .clientSecret("mock")
                .clientName("mock")
                .authorizationGrantTypes(new HashSet<>(Arrays.asList(GrantType.builder().value(AuthorizationGrantTypeEnum.authorization_code).build())))
                .clientAuthenticationMethods(new HashSet<>(Arrays.asList(AuthenticationMethod.builder().value(AuthenticationMethodEnum.client_secret_basic).build())))
                .redirectUris("mock").scopes("mock").build();
        Mockito.when(clientRepository.findById(anyString())).thenReturn(Optional.of(client));
        RegisteredClient registeredClient = registeredClientService.findById("mock");
        Mockito.verify(clientRepository, Mockito.atMostOnce()).findById(anyString());
        assertEquals(registeredClient.getClientId(), client.getClientId());
    }

    @Test
    void testFindByClientId() {
        Client client = Client.builder().id("mock").clientId("mock")
                .clientSecret("mock")
                .clientName("mock")
                .authorizationGrantTypes(new HashSet<>(Arrays.asList(GrantType.builder().value(AuthorizationGrantTypeEnum.authorization_code).build())))
                .clientAuthenticationMethods(new HashSet<>(Arrays.asList(AuthenticationMethod.builder().value(AuthenticationMethodEnum.client_secret_basic).build())))
                .redirectUris("mock").scopes("mock").build();
        Mockito.when(clientRepository.findByClientId(anyString())).thenReturn(Optional.of(client));
        RegisteredClient registeredClient = registeredClientService.findByClientId("mock");
        Mockito.verify(clientRepository, Mockito.atMostOnce()).findByClientId(anyString());
        assertEquals(registeredClient.getClientId(), client.getClientId());
    }
}