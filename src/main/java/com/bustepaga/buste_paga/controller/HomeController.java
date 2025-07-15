package com.bustepaga.buste_paga.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        System.out.println(">>> Home controller caricato");
        return "home"; // nome del file home.html in src/main/resources/templates
    }

    @GetMapping("/error")
    public String error() {
        return "error"; // crea error.html per mostrare messaggi
    }
}
