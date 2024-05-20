package com.springproject.offre_emploi.services;

import com.springproject.offre_emploi.Beans.Candidature;
import com.springproject.offre_emploi.Beans.CandidatureId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springproject.offre_emploi.Repository.CandidatureRepository;

import java.util.List;
import java.util.Optional;


@Service
public class CandidatureService {

    @Autowired
    private CandidatureRepository candidatureRepository;


    public Iterable<Candidature> getAllCandidatures() {
        return candidatureRepository.findAll();
    }

    public Optional<Candidature> getCandidatureById(CandidatureId id) {
        return candidatureRepository.findById(id);
    }

    public Candidature saveCandidature(Candidature candidature) {
        return candidatureRepository.save(candidature);
    }

    public void deleteCandidature(CandidatureId id) {
        candidatureRepository.deleteById(id);
    }
}
