package com.springproject.offre_emploi.Repository;

import com.springproject.offre_emploi.Beans.Recruteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecruteurRepository extends CrudRepository<Recruteur, Integer> {


}