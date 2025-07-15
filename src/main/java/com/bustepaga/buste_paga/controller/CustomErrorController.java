package com.bustepaga.buste_paga.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

@RequestMapping("/error")
public String handleError(HttpServletRequest request, Model model) {
    String errorMessage = (String) request.getSession().getAttribute("error.message");
    if (errorMessage == null) {
        errorMessage = "Si è verificato un errore sconosciuto.";
    }
    model.addAttribute("errorMessage", errorMessage);
    return "custom-error";
}

}
