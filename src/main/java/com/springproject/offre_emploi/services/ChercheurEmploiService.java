package com.springproject.offre_emploi.services;


import com.springproject.offre_emploi.Beans.ChercheurdEmploi;
import com.springproject.offre_emploi.Repository.ChercheurdEmploiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class ChercheurEmploiService {

    @Autowired
    private ChercheurdEmploiRepository chercheurEmploiRepository;


    public Iterable<ChercheurdEmploi> getAllChercheursEmploi() {
        return chercheurEmploiRepository.findAll();
    }

    public Optional<ChercheurdEmploi> getChercheurEmploiById(int id) {
        return chercheurEmploiRepository.findById(id);
    }

    public ChercheurdEmploi saveChercheurEmploi(ChercheurdEmploi chercheurEmploi) {
        return chercheurEmploiRepository.save(chercheurEmploi);
    }

    public void deleteChercheurEmploi(int id) {
        chercheurEmploiRepository.deleteById(id);
    }
}
