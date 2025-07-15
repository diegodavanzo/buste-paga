package com.bustepaga.buste_paga.security;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.bustepaga.buste_paga.model.Dipendente;
import com.bustepaga.buste_paga.repository.DipendenteRepository;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user = super.loadUser(userRequest);
        String email = user.getAttribute("email");

        Optional<Dipendente> dipendenteOpt = dipendenteRepository.findByEmail(email);
        if (dipendenteOpt.isEmpty()) {
            OAuth2Error oauth2Error = new OAuth2Error("invalid_token", "Accesso negato: email non autorizzata", "");
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        }

        // Qui puoi restituire un wrapper personalizzato o semplicemente l'utente
        return user;
    }
}
