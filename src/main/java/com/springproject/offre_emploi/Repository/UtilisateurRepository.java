package com.springproject.offre_emploi.Repository;


import com.springproject.offre_emploi.Beans.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Integer> {
     Utilisateur  findByMail(String mail);
}