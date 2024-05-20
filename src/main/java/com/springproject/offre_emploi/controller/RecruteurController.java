package com.springproject.offre_emploi.controller;

import com.springproject.offre_emploi.Beans.Recruteur;
import com.springproject.offre_emploi.services.RecruteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/recruteur")
public class RecruteurController {

    @Autowired
    private RecruteurService recruteurService;

    @GetMapping
    public ResponseEntity<Iterable<Recruteur>> getAllRecruteurs() {
        Iterable<Recruteur> recruteurs = recruteurService.getAllRecruteurs();
        return new ResponseEntity<>(recruteurs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recruteur> getRecruteurById(@PathVariable int id) {
        Optional<Recruteur> recruteur = recruteurService.getRecruteurById(id);
        return recruteur.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<Recruteur> saveRecruteur(@RequestBody Recruteur recruteur) {
        Recruteur savedRecruteur = recruteurService.saveRecruteur(recruteur);
        return new ResponseEntity<>(savedRecruteur, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecruteur(@PathVariable int id) {
        recruteurService.deleteRecruteur(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }





}
