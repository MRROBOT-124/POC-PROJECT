package com.realtime.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.realtime.project.entity.UserDetails;
import com.realtime.project.service.UserDetailsService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    private MockMvc mockMvc;
    @MockBean
    private UserDetailsService userDetailsService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach()
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testRegisterNewUser() throws Exception {
        UserDetails userDetails = UserDetails.builder().username("mock")
                .password("mock").email("mock").build();
        Mockito.when(userDetailsService.persistUser(any(UserDetails.class)))
                        .thenReturn(userDetails);
        mockMvc.perform(post("/user/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new ParameterNamesModule())
                                .registerModule(new Jdk8Module())
                                .registerModule(new JavaTimeModule()).writeValueAsString(userDetails)))
                .andExpect(status().isOk());
        Mockito.verify(userDetailsService, Mockito.atMostOnce()).persistUser(any(UserDetails.class));

    }

    @Test
    void testGetUserInfo() throws Exception {
        UserDetails userDetails = UserDetails.builder().username("mock")
                .password("mock").email("mock").build();
        Mockito.when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        mockMvc.perform(get("/user/get").param("username", "mock"))
                .andExpect(status().isOk());
        Mockito.verify(userDetailsService, Mockito.atMostOnce()).loadUserByUsername(anyString());
    }
}