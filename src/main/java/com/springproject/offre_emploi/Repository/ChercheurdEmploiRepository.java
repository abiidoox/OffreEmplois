package com.springproject.offre_emploi.Repository;


import com.springproject.offre_emploi.Beans.ChercheurdEmploi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChercheurdEmploiRepository extends CrudRepository<ChercheurdEmploi, Integer> {
}