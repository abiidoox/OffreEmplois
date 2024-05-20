package com.springproject.offre_emploi.services;

import com.springproject.offre_emploi.Beans.Offre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springproject.offre_emploi.Repository.OffreRepository;

import java.util.List;
import java.util.Optional;


@Service
public class OffreService {

    @Autowired
    private OffreRepository offreRepository;


    public Iterable<Offre> getAllOffres() {
        return offreRepository.findAll();
    }

    public Optional<Offre> getOffreById(int id) {
        return offreRepository.findById(id);
    }

    public Offre saveOffre(Offre offre) {
        return offreRepository.save(offre);
    }

    public void deleteOffre(int id) {
        offreRepository.deleteById(id);
    }


    public List<Object[]> findMostEntreprisePosts() {

        List<Object[]> list = offreRepository.findMostEntreprisePosts();
            return list;
    }
}
