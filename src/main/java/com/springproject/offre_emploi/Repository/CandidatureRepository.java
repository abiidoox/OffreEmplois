package com.springproject.offre_emploi.Repository;


import com.springproject.offre_emploi.Beans.Candidature;
import com.springproject.offre_emploi.Beans.CandidatureId;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CandidatureRepository extends CrudRepository<Candidature, CandidatureId> {
    long countById_OffId(Integer offId);
    List<Candidature> findById_OffId(Integer offId, Sort sort);
    long countDistinctById_OffId(Integer offId);

    long deleteById_CheIdAndId_OffId(Integer cheId, Integer offId);
    List<Candidature> findByChe_Id(Integer id, Sort sort);
    @Query("""
    SELECT c FROM Candidature c
        WHERE (UPPER(c.off.titre) LIKE UPPER(CONCAT('%', ?1, '%')) OR UPPER(c.off.description) LIKE UPPER(CONCAT('%', ?2, '%')))
    AND c.che.id = ?3 
    ORDER BY c.dateCandidature DESC
""")
    List<Candidature> findByOff_TitreContainsIgnoreCaseOrOff_DescriptionContainsIgnoreCaseAndChe_IdAllIgnoreCase(String titre, String description, Integer id, Sort sort);

    long countByChe_Id(Integer id);
    long countByOff_Rec_Id(Integer id);
}