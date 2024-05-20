package com.springproject.offre_emploi.Repository;

import com.springproject.offre_emploi.Beans.Administrateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministrateurRepository extends CrudRepository<Administrateur, Integer> {
}