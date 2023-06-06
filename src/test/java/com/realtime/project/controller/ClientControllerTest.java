package com.realtime.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.realtime.project.constants.AuthenticationMethodEnum;
import com.realtime.project.constants.AuthorizationGrantTypeEnum;
import com.realtime.project.entity.AuthenticationMethod;
import com.realtime.project.entity.Client;
import com.realtime.project.entity.GrantType;
import com.realtime.project.resolvers.RegisteredClientResolvers;
import com.realtime.project.service.RegisteredClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    private MockMvc mockMvc;
    @MockBean
    private RegisteredClientService registeredClientService;
    @MockBean
    private RegisteredClientResolvers registeredClientResolvers;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach()
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testRegisterClient() throws Exception {
        Client client = Client.builder().id("mock").clientId("mock")
                .clientSecret("mock")
                .clientName("mock")
                .authorizationGrantTypes(new HashSet<>(Collections.singletonList(GrantType.builder().value(AuthorizationGrantTypeEnum.AUTHORIZATION_CODE).build())))
                .clientAuthenticationMethods(new HashSet<>(Collections.singletonList(AuthenticationMethod.builder().value(AuthenticationMethodEnum.CLIENT_SECRET_BASIC).build())))
                .redirectUris("mock").scopes("mock").build();
        Mockito.when(registeredClientService.save(any(Client.class))).thenReturn(client);
        mockMvc.perform(post("/client/add")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new ParameterNamesModule())
                                .registerModule(new Jdk8Module())
                                .registerModule(new JavaTimeModule()).writeValueAsString(client)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetClient() throws Exception {
        RegisteredClient registeredClient = RegisteredClient.withId("mock").clientId("mock")
                .clientSecret("mock")
                .clientName("mock")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .redirectUri("mock").scope("mock").build();
        Mockito.when(registeredClientService.findByClientId(anyString())).thenReturn(registeredClient);
        mockMvc.perform(get("/client/get").param("clientID", "mock")).andExpect(status().isOk());
    }

    @Test
    void testRegisterClientGraphQL() throws Exception {
        String query="";
        Mockito.when(registeredClientResolvers.registerClientGraphQL(any(Client.class))).thenReturn(new Client());
        mockMvc.perform(post("/graphql")
                .content(query))
                .andExpect(status().isOk());

    }


}