package com.springproject.offre_emploi.services;

import com.springproject.offre_emploi.Beans.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.springproject.offre_emploi.Repository.UtilisateurRepository;

import java.util.List;
import java.util.Optional;


@Service
public class UtilisateurService  implements  userService{

    @Autowired
    private  UtilisateurRepository utilisateurRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Utilisateur findUserByEmail(String email) {
        return utilisateurRepository.findByMail(email);
    }

    public Iterable<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(int id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur)
    {
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        return utilisateurRepository.save(utilisateur);
    }
    public void deleteUtilisateur(int id) {
        utilisateurRepository.deleteById(id);
    }
}

