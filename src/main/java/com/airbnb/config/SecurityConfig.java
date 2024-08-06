package com.airbnb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception{

        //h(cd)2
        http.csrf().disable().cors().disable();

        //harp
        http.authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/login", "/api/v1/auth/addUser")
                .permitAll().anyRequest().authenticated();
        return http.build();

    }
}
