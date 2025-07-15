package com.bustepaga.buste_paga.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        String errorMessage = "Errore sconosciuto";

        if (exception instanceof OAuth2AuthenticationException) {
            errorMessage = ((OAuth2AuthenticationException) exception).getError().getDescription();
        } else if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                errorMessage = "Accesso negato: non hai i permessi necessari.";
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                errorMessage = "Non autenticato. Effettua il login.";
            } else {
                errorMessage = "Errore HTTP: " + statusCode;
            }
        }

        model.addAttribute("errorMessage", errorMessage);
        return "custom-error";
    }
}
