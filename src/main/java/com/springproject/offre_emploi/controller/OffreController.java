package com.springproject.offre_emploi.controller;

import com.springproject.offre_emploi.Beans.Offre;
import com.springproject.offre_emploi.Beans.Recruteur;
import com.springproject.offre_emploi.Beans.Utilisateur;
import com.springproject.offre_emploi.Repository.CandidatureRepository;
import com.springproject.offre_emploi.Repository.OffreRepository;
import com.springproject.offre_emploi.Repository.RecruteurRepository;
import com.springproject.offre_emploi.Repository.UtilisateurRepository;
import com.springproject.offre_emploi.services.OffreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Controller
public class OffreController {

    @Autowired
    private OffreService offreService;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private RecruteurRepository recruteurRepository;
    @Autowired
    private OffreRepository offreRepository;
    @Autowired
    private IndexController indexController;
    @Autowired
    private CandidatureRepository candidatureRepository;

    @GetMapping
    public ResponseEntity<Iterable<Offre>> getAllOffres() {
        Iterable<Offre> offres = offreService.getAllOffres();
        return new ResponseEntity<>(offres, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offre> getOffreById(@PathVariable int id) {
        Optional<Offre> offre = offreService.getOffreById(id);
        return offre.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/offres/save")
    public String saveOffre(Offre offre) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Utilisateur utilisateur = utilisateurRepository.findByMail(userDetails.getUsername());
        Recruteur recruteur = recruteurRepository.findById(utilisateur.getId()).get();
        offre.setRec(recruteur);
        offre.setDatePublication(Instant.now());
        Offre savedOffre = offreService.saveOffre(offre);
        return "redirect:/to_dashbord";
    }

    @GetMapping("/offres/delete/{id}")
    public String deleteOffre(@PathVariable int id) {
        offreService.deleteOffre(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        if (userRole.equals("admin"))
            return "redirect:/offres_List";
        else
            return "redirect:/to_dashbord";

    }

    @GetMapping("/offres/to_edit/{id}")
    public String toeditOffre(@PathVariable int id, Model model){
        Offre offre = offreService.getOffreById(id).get();
        model.addAttribute("offre", offre);
        return "Edit_Offre";
    }

    @GetMapping("/Edit_Offre/{id}")
    public String editOffre(@PathVariable int id, Model model){
        Offre offre = offreService.getOffreById(id).get();
        model.addAttribute("offre", offre);
        return "Edit_Offre";
    }
    @GetMapping("/offres/to_add")
    public String editOffre() {
        return "Add_Offre";
    }

    @GetMapping("/offres/{cle}")
    public String findbyTitreOrDescription(String cle,Model model){
        cle="%"+cle+"%";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Utilisateur utilisateur = utilisateurRepository.findByMail(userDetails.getUsername());
        Recruteur recruteur=recruteurRepository.findById(utilisateur.getId()).get();
        List<Offre> list= offreRepository.findByTitreLikeIgnoreCaseOrDescriptionLikeIgnoreCaseAndRecOrderByDatePublicationDesc(cle,cle,recruteur, Sort.by("datePublication").descending());
        model.addAttribute("offres", list);
        model.addAttribute("countoffer", indexController.countOffre(authentication));
        model.addAttribute("counPersonne", indexController.countPersonneAlloffers(authentication));
        return "Dashboard-Recruteur";
    }


    @GetMapping("/offres/select/{id}")
    public String getOffre(@PathVariable("id") int id,Model model){
        Offre offre = offreRepository.findFirstById(id);
        if (offre != null) {
            model.addAttribute("offre", offre);
            model.addAttribute("nbr_candidats", candidatureRepository.countById_OffId(id));
            model.addAttribute("ListesCandidates", candidatureRepository.findById_OffId(id, Sort.by("dateCandidature").descending()));
            return "OffreDetailForRecruteur";
        }
        return "redirect:/to_dashbord";
    }

    @GetMapping("/Offre/select/Employer/{id}")
    public String getOffreEmp(@PathVariable("id") int id,Model model) {
        Offre offre = offreRepository.findFirstById(id);
        if (offre != null) {
            model.addAttribute("offre", offre);
            model.addAttribute("nbr_candidats", candidatureRepository.countById_OffId(id));
            return "OffreDetailForEmployer";
        }
        return "redirect:/to_dashbord";
    }
}
