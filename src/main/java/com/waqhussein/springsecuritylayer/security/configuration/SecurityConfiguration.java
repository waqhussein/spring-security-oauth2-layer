package com.waqhussein.springsecuritylayer.security.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

/*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
*/
/*
        httpSecurity
                    .authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                .httpBasic().;
*//*

        return httpSecurity.build();
    }
*/
}
