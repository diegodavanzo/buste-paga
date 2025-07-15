package com.bustepaga.buste_paga.security;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.bustepaga.buste_paga.model.Dipendente;
import com.bustepaga.buste_paga.repository.DipendenteRepository;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println(">>> CustomOAuth2UserService.loadUser() chiamato");

        OAuth2User user = super.loadUser(userRequest);
        String email = user.getAttribute("email");

        System.out.println("Email ricevuta da OAuth2: " + email);
        System.out.println(">>> CustomOAuth2UserService.loadUser() inizio, email="+email);


        Optional<Dipendente> dipendenteOpt = dipendenteRepository.findByEmail(email);
        if (dipendenteOpt.isEmpty()) {
            System.out.println("Email NON autorizzata: " + email);
            OAuth2Error oauth2Error = new OAuth2Error("invalid_token", "Accesso negato: email non autorizzata", "");
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        }

        System.out.println("Email autorizzata: " + email);
        return user;
    }
}
