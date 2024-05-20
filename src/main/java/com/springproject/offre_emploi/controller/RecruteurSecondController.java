package com.springproject.offre_emploi.controller;

import com.springproject.offre_emploi.Beans.Offre;
import com.springproject.offre_emploi.Beans.Recruteur;
import com.springproject.offre_emploi.Beans.Utilisateur;
import com.springproject.offre_emploi.Repository.RecruteurRepository;
import com.springproject.offre_emploi.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class RecruteurSecondController {
    @Autowired
    RecruteurRepository recruteurRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping("/rec/edit")
    public String saveRecruteur(Recruteur recruteur, Authentication authentication) {
        System.out.println("savedRecruteur = " +recruteur);
        String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
        Utilisateur utilisateur = utilisateurRepository.findByMail(((UserDetails) authentication.getPrincipal()).getUsername());
        recruteur.setUtilisateurs(utilisateur);
         recruteurRepository.save(recruteur);
        return "redirect:/to_dashbord";
    }



}
