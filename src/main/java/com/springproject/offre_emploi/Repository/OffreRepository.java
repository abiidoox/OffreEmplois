package com.springproject.offre_emploi.Repository;

import com.springproject.offre_emploi.Beans.Offre;
import com.springproject.offre_emploi.Beans.Recruteur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffreRepository extends CrudRepository<Offre, Integer> {
    @Query("""
            select o from Offre o
            where (upper(o.titre) like upper(concat('%', ?1, '%')) or upper(o.description) like upper(concat('%', ?2, '%'))) """)
    Page<Offre> findByTitreContainsIgnoreCaseOrDescriptionContainsIgnoreCaseAndRec_LieuIgnoreCase(String titre, String description, Pageable pageable);

    @Query("select o.rec.entreprise,count(o) as offre_count from Offre o group by o.rec.entreprise order by offre_count desc limit 5")
    List<Object[]> findMostEntreprisePosts();

    @Query("select  o.rec.lieu,count(o) as offer_count from Offre o group by o.rec.lieu order by offer_count desc limit 3")
    List<Object[]> findTopCitiesWithOffers();

    @Query("select count(o) from Offre o where month(o.datePublication)= ?1 and year(o.datePublication)=year(current date)")
    int CountOfferByMonth(int month);
    Offre findFirstById(Integer id);
    @Query("""
            select o from Offre o
            where ( upper(o.titre) like upper(concat('%', ?1, '%')) or upper(o.description) like upper(concat('%', ?2, '%'))) or o.rec.lieu = ?3
            order by o.datePublication""")
    List<Offre> findOffresByTitreOrDescriptionoAndLieu(String titre, String description, String lieu);

    List<Offre> findByRec_Id(Integer id);
    long countByRec_Id(Integer id);
    long countById(Integer id);



    @Query("""
            select o from Offre o
            where ( upper(o.titre) like upper(?1) or upper(o.description) like upper(?2) ) and o.rec = ?3
            order by o.datePublication DESC""")
    List<Offre> findByTitreLikeIgnoreCaseOrDescriptionLikeIgnoreCaseAndRecOrderByDatePublicationDesc(String titre, String description, Recruteur rec, Sort sort);


    List<Offre> findAllByOrderByDatePublicationDesc(Sort sort);
}
