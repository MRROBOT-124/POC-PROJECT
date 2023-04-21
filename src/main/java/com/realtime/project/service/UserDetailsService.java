package com.realtime.project.service;

import com.realtime.project.entity.Authorities;
import com.realtime.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * STORE USER DETAILS STORED IN POSTGRESQL DATABASE
 * THIS CLASS CONTAINS METHODS THAT CAN PULL THE NECESSARY RECORDS
 * NEEDED.
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * USED TO GET THE USER INFO FROM THE DATABASE
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.realtime.project.entity.UserDetails> optionalUserDetails = userRepository.findById(username);
        return optionalUserDetails.get();
    }

    /**
     * USED TO PERSIST USER INFORMATION INTO THE DATABASE
     * @param userDetails
     * @return
     */
    public com.realtime.project.entity.UserDetails persistUser(com.realtime.project.entity.UserDetails userDetails) {
        userDetails.setAuthorities(userDetails.getAuthoritiesList());
        userDetails.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        return userRepository.save(userDetails);
    }
}
