package com.bustepaga.buste_paga.config;

import com.bustepaga.buste_paga.security.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login**", "/error").permitAll()  // pagine pubbliche
                .anyRequest().authenticated()  // tutto il resto autenticato
            )
            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService) // usa il custom service
                )
                .defaultSuccessUrl("/home", true) // reindirizza dopo login riuscito
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/") // dove tornare al logout
            );

        return http.build();
    }
}
