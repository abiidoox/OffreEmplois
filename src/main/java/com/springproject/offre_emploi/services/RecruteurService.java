package com.springproject.offre_emploi.services;

import com.springproject.offre_emploi.Beans.Recruteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springproject.offre_emploi.Repository.RecruteurRepository;

import java.util.List;
import java.util.Optional;


@Service
public class RecruteurService {

    @Autowired
    private RecruteurRepository recruteurRepository;

    public Iterable<Recruteur> getAllRecruteurs() {
        return recruteurRepository.findAll();
    }

    public Optional<Recruteur> getRecruteurById(int id) {
        return recruteurRepository.findById(id);
    }

    public Recruteur saveRecruteur(Recruteur recruteur) {
        return recruteurRepository.save(recruteur);
    }

    public void deleteRecruteur(int id) {
        recruteurRepository.deleteById(id);
    }
}

