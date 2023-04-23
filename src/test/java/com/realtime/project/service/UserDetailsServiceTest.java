package com.realtime.project.service;

import com.realtime.project.entity.UserInfo;
import com.realtime.project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceTest {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserInfoService userDetailsService;

    @Test
    void testLoadUserByUsername() {
        UserInfo userDetails = UserInfo.builder().username("mock")
                        .password("mock").email("mock").build();
        Mockito.when(userRepository.findById(anyString())).thenReturn(Optional.of(userDetails));
        org.springframework.security.core.userdetails.UserDetails userByUsername = userDetailsService.loadUserByUsername(userDetails.getUsername());
        Mockito.verify(userRepository, Mockito.atMostOnce()).findById(anyString());
        assertEquals(userByUsername.getUsername(), userDetails.getUsername());
    }

    @Test
    void testPersistUser() {
        UserInfo userDetails = UserInfo.builder().username("mock")
                .password("mock").email("mock").build();
        Mockito.when(userRepository.save(any(UserInfo.class)))
                .thenReturn(userDetails);
        UserInfo persistUser = userDetailsService.persistUser(userDetails);
        Mockito.verify(userRepository, Mockito.atMostOnce()).save(any(UserInfo.class));
        assertEquals(persistUser.getUsername(), userDetails.getUsername());

    }
}