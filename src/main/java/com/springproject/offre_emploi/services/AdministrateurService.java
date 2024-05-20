package com.springproject.offre_emploi.services;

import com.springproject.offre_emploi.Beans.Administrateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springproject.offre_emploi.Repository.AdministrateurRepository;

import java.util.List;
import java.util.Optional;


@Service
public class AdministrateurService {

    @Autowired
    private AdministrateurRepository administrateurRepository;


    public Iterable<Administrateur> getAllAdministrateurs() {
        return administrateurRepository.findAll();
    }

    public Optional<Administrateur> getAdministrateurById(int id) {
        return administrateurRepository.findById(id);
    }

    public Administrateur saveAdministrateur(Administrateur administrateur) {
        return administrateurRepository.save(administrateur);
    }

    public void deleteAdministrateur(int id) {
        administrateurRepository.deleteById(id);
    }
}
