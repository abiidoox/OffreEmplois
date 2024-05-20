package com.springproject.offre_emploi.controller;

import com.springproject.offre_emploi.Beans.ChercheurdEmploi;
import com.springproject.offre_emploi.services.ChercheurEmploiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/chercheursdemploi")
public class ChercheurdEmploiController {

    @Autowired
    private ChercheurEmploiService chercheurdEmploiService;

    @GetMapping
    public ResponseEntity<Iterable<ChercheurdEmploi>> getAllChercheursdEmploi() {
        Iterable<ChercheurdEmploi> chercheursdEmploi = chercheurdEmploiService.getAllChercheursEmploi();
        return new ResponseEntity<>(chercheursdEmploi, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChercheurdEmploi> getChercheurdEmploiById(@PathVariable int id) {
        Optional<ChercheurdEmploi> chercheurdEmploi = chercheurdEmploiService.getChercheurEmploiById(id);
        return chercheurdEmploi.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ChercheurdEmploi> saveChercheurdEmploi(@RequestBody ChercheurdEmploi chercheurdEmploi) {
        ChercheurdEmploi savedChercheurdEmploi = chercheurdEmploiService.saveChercheurEmploi(chercheurdEmploi);
        return new ResponseEntity<>(savedChercheurdEmploi, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChercheurdEmploi(@PathVariable int id) {
        chercheurdEmploiService.deleteChercheurEmploi(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
