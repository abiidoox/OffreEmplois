package com.springproject.offre_emploi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class chooseProfil {


    @GetMapping("/chooseProfil")
    public String defaultRun() {
        // Add your logic here
        return "chooseProfil"; // Return the name of your default view template
    }
    @GetMapping("/RegistreEmploye")
    public String defaultRun1() {
        // Add your logic here
        return "RegistreEmploye"; // Return the name of your default view template
    }
    @GetMapping("/RegistreRecruteur")
    public String defaultRun2() {
        // Add your logic here
        return "RegistreRecruteur"; // Return the name of your default view template
    }
}
