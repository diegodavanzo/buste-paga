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
        System.out.println("✅ [CustomOAuth2UserService] loadUser() invocato");

        // Carica i dati base dall'OAuth provider (Google, etc.)
        OAuth2User user = super.loadUser(userRequest);

        // Prendi l'email restituita dal provider
        String email = user.getAttribute("email");
        System.out.println("📧 [CustomOAuth2UserService] Email ricevuta da OAuth2: " + email);

        // Controlla se la mail esiste nel DB tra i dipendenti autorizzati
        Optional<Dipendente> dipendenteOpt = dipendenteRepository.findByEmail(email);

        if (dipendenteOpt.isEmpty()) {
            System.out.println("❌ [CustomOAuth2UserService] Email NON autorizzata: " + email);
            OAuth2Error oauth2Error = new OAuth2Error("invalid_token", "Accesso negato: email non autorizzata", "");
            throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
        }

        System.out.println("✅ [CustomOAuth2UserService] Email autorizzata: accesso consentito");
        // Puoi qui restituire un wrapper personalizzato, oppure il user così com'è
        return user;
    }
}
