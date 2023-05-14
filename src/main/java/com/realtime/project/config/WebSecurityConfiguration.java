package com.realtime.project.config;

import com.realtime.project.constants.HelperConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {



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
        http.csrf(csrf -> csrf.ignoringRequestMatchers(HelperConstants.ALLOW_ALL_USER_ROUTES, HelperConstants.ALLOW_ALL_CLIENT_ROUTES, HelperConstants.ALLOW_ALL_OAUTH2_ROUTES, HelperConstants.ALLOW_ACTUATOR_ENDPOINTS))
                .authorizeHttpRequests(authorizeRequests ->
              authorizeRequests.anyRequest().authenticated()
        ).formLogin(Customizer.withDefaults());
        return http.build();
    }



}
