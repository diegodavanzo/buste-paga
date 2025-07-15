package com.bustepaga.buste_paga.security;

import com.bustepaga.buste_paga.model.Dipendente;
import com.bustepaga.buste_paga.service.DipendenteService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final DipendenteService dipendenteService;

    public CustomOAuth2UserService(DipendenteService dipendenteService) {
        this.dipendenteService = dipendenteService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);

        Map<String, Object> attributes = oauth2User.getAttributes();
        String email = (String) attributes.get("email");

        Optional<Dipendente> dipendenteOpt = dipendenteService.findByEmail(email);

        if (dipendenteOpt.isEmpty()) {
            throw new OAuth2AuthenticationException("Accesso negato: email non autorizzata");
        }

        return oauth2User;
    }
}
