package com.springproject.offre_emploi.controller;

import com.springproject.offre_emploi.Beans.ChartData;
import com.springproject.offre_emploi.Beans.ChercheurdEmploi;
import com.springproject.offre_emploi.Beans.Utilisateur;
import com.springproject.offre_emploi.Repository.UtilisateurRepository;
import com.springproject.offre_emploi.services.ChartDataService;
import com.springproject.offre_emploi.services.ChercheurEmploiService;
import com.springproject.offre_emploi.services.RecruteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
public class ChartDataController{

    @Autowired
    ChartDataService chartDataService;

    @Autowired
    private RecruteurService recruteurService;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private ChercheurEmploiService chercheurEmploiService;


    @GetMapping("/api/posts-data")
    public List<Integer> getPostsData() {
        // Retrieve the data from your data source (e.g., database)

        List<Integer> postsData =chartDataService.getChartData().getData();

        return postsData;
    }

    @GetMapping("/api/posts-data-most-city")
    public List<Object[]> getMostCityPostsData() {
        // Retrieve the data from your data source (e.g., database)
        return chartDataService.getChartData2();
    }


    @GetMapping("/Username")
    public String getUsername() {
        // Retrieve the data from your data source (e.g., database)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        if (userRole.equals("admin"))
            return "Admin";
        else if(userRole.equals("recruteur"))
            return  recruteurService.getRecruteurById(utilisateurRepository.findByMail(authentication.getName()).getId()).get().getEntreprise();
        else if(userRole.equals("Employer")) {
            ChercheurdEmploi chercheurdEmploi = chercheurEmploiService.getChercheurEmploiById(utilisateurRepository.findByMail(authentication.getName()).getId()).get();
            return chercheurdEmploi.getNom()+ " "+chercheurdEmploi.getPrenom();
        }
        else
            return "error";
    }

}
