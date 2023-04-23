package com.realtime.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Autowired
    private com.realtime.project.service.UserDetailsService userDetailsService;

    /**
     * SETTING DEFAULT WEB SECURITY FOR PROVIDING ACCESS TO THE END POINTS
     * ADDED CUSTOM USER DETAILS SERVICE IMPLEMENTATION FOR GETTING THE
     * USER DETAILS FROM THE DATABASE
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .userDetailsService(userDetailsService)
                .authorizeHttpRequests(authorizeRequests ->
              authorizeRequests.anyRequest().authenticated()
        ).formLogin(Customizer.withDefaults());
        return http.build();
    }



}
