package com.springproject.offre_emploi.controller;

import com.springproject.offre_emploi.Beans.*;
import com.springproject.offre_emploi.Repository.CandidatureRepository;
import com.springproject.offre_emploi.Repository.ChercheurdEmploiRepository;
import com.springproject.offre_emploi.Repository.UtilisateurRepository;
import com.springproject.offre_emploi.services.CandidatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/candidatures")
public class CandidatureController {


    @Autowired
    private CandidatureService candidatureService;
    @GetMapping
    public ResponseEntity<Iterable<Candidature>> getAllCandidatures() {
        Iterable<Candidature> candidatures = candidatureService.getAllCandidatures();
        return new ResponseEntity<>(candidatures, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Candidature> getCandidatureById(@PathVariable CandidatureId id) {
        Optional<Candidature> candidature = candidatureService.getCandidatureById(id);
        return candidature.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Candidature> saveCandidature(@RequestBody Candidature candidature) {
        Candidature savedCandidature = candidatureService.saveCandidature(candidature);
        return new ResponseEntity<>(savedCandidature, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidature(@PathVariable CandidatureId id) {
        candidatureService.deleteCandidature(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}
