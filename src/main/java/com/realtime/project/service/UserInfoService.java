package com.realtime.project.service;

import com.realtime.project.entity.UserInfo;
import com.realtime.project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * STORE USER DETAILS STORED IN POSTGRESQL DATABASE
 * THIS CLASS CONTAINS METHODS THAT CAN PULL THE NECESSARY RECORDS
 * NEEDED.
 */
@Slf4j
@Service
@Transactional
public class UserInfoService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * USED TO GET THE USER INFO FROM THE DATABASE
     * @param username the username identifying the user whose data is required.
     * @return "USER DETAILS"
     * @throws UsernameNotFoundException "USERNAME NOT FOUND"
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> optionalUserDetails = userRepository.findById(username);
        if(optionalUserDetails.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return optionalUserDetails.get();
    }

    /**
     * USED TO PERSIST USER INFORMATION INTO THE DATABASE
     * @param userDetails "USER DETAILS"
     * @return "USER INFO"
     */
    public UserInfo persistUser(UserInfo userDetails) {
        userDetails.setAuthorities(userDetails.getAuthoritiesList());
        userDetails.setPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        return userRepository.save(userDetails);
    }

    /**
     * USED TO GET THE USER INFO FROM THE DATABASE
     * @param username the username identifying the user whose data is required.
     * @return "USER INFO"
     * @throws UsernameNotFoundException "USERNAME NOT FOUND"
     */
    public UserInfo findUser(String username) throws UsernameNotFoundException {
        log.info("Entered UserInfoService ---> findUser() ---> Attempting to find User details by using username");
        Optional<UserInfo> optionalUserDetails = userRepository.findById(username);
        if(optionalUserDetails.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        log.info("Entered UserInfoService ---> findUser() ---> Successfully retrieved user");
        return optionalUserDetails.get();
    }

    /**
     * USED TO GET THE USER INFO FROM THE DATABASE
     * @return "USER INFO"
     * @throws UsernameNotFoundException "USERNAME NOT FOUND"
     */
    public List<UserInfo> findAllUsers() throws UsernameNotFoundException {
        log.info("Entered UserInfoService ---> findAllUsers() ---> Attempting to find User details by using username");
        List<UserInfo> userInfoList = userRepository.findAll();
        if(userInfoList.isEmpty()) {
            throw new UsernameNotFoundException("No Data Available");
        }
        log.info("Entered UserInfoService ---> findUser() ---> Successfully retrieved user");
        return userInfoList;
    }
}
