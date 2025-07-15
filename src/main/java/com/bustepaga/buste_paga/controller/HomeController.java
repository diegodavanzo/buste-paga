package com.bustepaga.buste_paga.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home"; // nome della pagina HTML (src/main/resources/templates/home.html)
    }
}
