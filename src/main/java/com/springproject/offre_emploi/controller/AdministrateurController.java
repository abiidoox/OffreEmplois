package com.springproject.offre_emploi.controller;

import com.springproject.offre_emploi.Beans.Administrateur;
import com.springproject.offre_emploi.services.AdministrateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/administrateurs")
public class AdministrateurController {

    @Autowired
    private AdministrateurService administrateurService;

    @GetMapping
    public ResponseEntity<Iterable<Administrateur>> getAllAdministrateurs() {
        Iterable<Administrateur> administrateurs = administrateurService.getAllAdministrateurs();
        return new ResponseEntity<>(administrateurs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrateur> getAdministrateurById(@PathVariable int id) {
        Optional<Administrateur> administrateur = administrateurService.getAdministrateurById(id);
        return administrateur.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Administrateur> saveAdministrateur(@RequestBody Administrateur administrateur) {
        Administrateur savedAdministrateur = administrateurService.saveAdministrateur(administrateur);
        return new ResponseEntity<>(savedAdministrateur, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdministrateur(@PathVariable int id) {
        administrateurService.deleteAdministrateur(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
