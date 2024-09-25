package com.mendonca.file.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())) 
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers("/h2-console").permitAll()
            	.requestMatchers("/h2-console/**").permitAll() 
                .anyRequest().permitAll()
                
            );
            

        return http.build();
    }
}


