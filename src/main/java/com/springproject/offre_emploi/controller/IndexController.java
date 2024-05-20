package com.springproject.offre_emploi.controller;

import com.springproject.offre_emploi.Beans.*;
import com.springproject.offre_emploi.Repository.*;
import com.springproject.offre_emploi.services.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    private RecruteurService recruteurService;
    @Autowired
    private OffreRepository offreRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private ChercheurdEmploiRepository chercheurdEmploiRepository;
    @Autowired
    private CandidatureRepository candidatureRepository;
    @Autowired
    private RecruteurRepository recruteurRepository;
    @Autowired
    private CandidatureService candidatureService;
    @Autowired
    private LoginController loginController;
    @Autowired
    private OffreService offreService;

    @Autowired
    private ChercheurEmploiService chercheurEmploiService;

    @Autowired
    private JobService jobService;

    @Autowired
    private JobService2 jobService2;

    @Autowired
    private JobServiceBase jobServiceBase;

    @GetMapping("/")
    public String defaultRun(Model model) throws InterruptedException {
        // Add your logic here
        int page=1;
        String jobSearch="";
        Map<Link, Offre> map = new HashMap<>();
        map.putAll(scrapeJobs(jobSearch,page));
         model.addAttribute("scrapedData", map);
         model.addAttribute("numberPages", pages);
         model.addAttribute("jobSearch", jobSearch);
         model.addAttribute("page", page);
        return "index.html"; // Return the name of your default view template
    }
    public static ChromeDriver initialize() {

        //System.setProperty("webdriver.chrome.driver", "path/to/new/chromedriver.exe");
      //  WebDriver driver = new ChromeDriver();

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\abiid\\Documents\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless"); // Add this line to enable headless mode
       // options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.82 Safari/537.36");
        options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.6045.124 Safari/537.36");
        options.addArguments("--window-size=1366,768");
        return new ChromeDriver(options);
    }

    int pages;
    public Map<Link, Offre> scrapeJobs(String test,int page) throws InterruptedException {

        Map<Link, Offre> map = new HashMap<>();
        map.putAll(jobService.printObjects(test,page));
        int s1= JobService.getNumberOfPages(test);
        map.putAll(jobService2.printObjects(test,page));
        int s2= JobService2.getNumberOfPages(test);

        map.putAll(jobServiceBase.printObjects(test,page));

        pages=Math.min(s1,s2);
        return map;
    }
    @GetMapping("/index")
    public String defaultRun2(Model model,@RequestParam(value = "page",required = false) Integer page, @RequestParam("jobSearch") String jobSearch) throws InterruptedException {
        // Add your logic here
        if(page==null)
            page=1;
        Map<Link, Offre> map = new HashMap<>();
        map.putAll(scrapeJobs(jobSearch,page));
        model.addAttribute("scrapedData", map);
        model.addAttribute("numberPages", pages);
        model.addAttribute("jobSearch", jobSearch);
        model.addAttribute("page", page);
        return "index.html"; // Return the name of your default view template
    }

    @GetMapping("/to_dashbord")
    public String defaultRunx(Model model, ModelAndView mav) {
        // Add your logic here
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = authentication.getAuthorities().iterator().next().getAuthority();
        if (userRole.equals("admin")) {
            Long count=offreRepository.count();
            model.addAttribute("countoffer", count);
            model.addAttribute("counChercheur",  chercheurdEmploiRepository.count());
            model.addAttribute("counRecruteur", recruteurRepository.count());
            model.addAttribute("ListMostEntreprisePosts", offreService.findMostEntreprisePosts());
            return "Dashboard-Admin";
            } else if (userRole.equals("recruteur"))
            {
            model.addAttribute("countoffer", countOffre(authentication));
            model.addAttribute("counPersonne", countPersonneAlloffers(authentication));
            model.addAttribute("offres", listeOffres(authentication));
            return "Dashboard-Recruteur"; // Return the name of your default view template
        }
            else if (userRole.equals("Employer")) {
            model.addAttribute("countofferpostuler", countofferpostuler(authentication));
            model.addAttribute("ListCandidatures", ListCandidatures(authentication));
            return "Dashboard-Employer"; // Return the name of your default view template
        }
            else
                return "index.html"; // Return the name of your default view template
        }

    @GetMapping("/Employeres")
    public String Employeres(Model model) {
        model.addAttribute("counChercheur",  chercheurdEmploiRepository.count());
        model.addAttribute("chercheur",  chercheurdEmploiRepository.findAll());
        return "Employeres";
    }
        public Long countOffre (Authentication authentication) {
            String userId =null;
            Long nbr=0L;
            if (authentication != null && authentication.isAuthenticated()) {
                if (authentication.getPrincipal() instanceof UserDetails) {
                    userId = ((UserDetails) authentication.getPrincipal()).getUsername();
                    Utilisateur utilisateur = utilisateurRepository.findByMail(((UserDetails) authentication.getPrincipal()).getUsername());
                  nbr= offreRepository.countByRec_Id(utilisateur.getId());
                }

            }
            return nbr;
        }

        public Long countofferpostuler(Authentication authentication)
        {
            String userId =null;
            Long nbr=0L;
            if (authentication != null && authentication.isAuthenticated()) {

                if (authentication.getPrincipal() instanceof UserDetails) {
                    userId = ((UserDetails) authentication.getPrincipal()).getUsername();
                    Utilisateur utilisateur = utilisateurRepository.findByMail(((UserDetails) authentication.getPrincipal()).getUsername());
                    nbr= candidatureRepository.countByChe_Id(utilisateur.getId());
                }
            }
            return nbr;
        }
        public  Long countPersonneAlloffers(Authentication authentication)
        {
            String userId =null;
            Long nbr=0L;
            if (authentication != null && authentication.isAuthenticated()) {

                if (authentication.getPrincipal() instanceof UserDetails) {
                    userId = ((UserDetails) authentication.getPrincipal()).getUsername();
                    Utilisateur utilisateur = utilisateurRepository.findByMail(((UserDetails) authentication.getPrincipal()).getUsername());
                    nbr= candidatureRepository.countByOff_Rec_Id(utilisateur.getId());
                }
            }
            return nbr;
        }
        public List<Offre> listeOffres(Authentication authentication) {
        String userId = null;
        ArrayList<Offre> offres = new ArrayList();
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                userId = ((UserDetails) authentication.getPrincipal()).getUsername();
                Utilisateur utilisateur = utilisateurRepository.findByMail(((UserDetails) authentication.getPrincipal()).getUsername());
                 offres =(ArrayList<Offre>) offreRepository.findByRec_Id(utilisateur.getId());
            }
        }
        return offres;
    }

        public  List<Candidature>ListCandidatures(Authentication authentication)
        {
            String userId =null;
            ArrayList<Candidature> candidatures = new ArrayList();
            if (authentication != null && authentication.isAuthenticated()) {

                if (authentication.getPrincipal() instanceof UserDetails) {
                    userId = ((UserDetails) authentication.getPrincipal()).getUsername();
                    Utilisateur utilisateur = utilisateurRepository.findByMail(((UserDetails) authentication.getPrincipal()).getUsername());
                    candidatures =(ArrayList<Candidature>) candidatureRepository.findByChe_Id(utilisateur.getId(), Sort.by("dateCandidature").descending());
                }
            }
            return candidatures;

        }
        {

        }



    @GetMapping("/toProfilRecruteur")
    public String toProfilRecruteur(Authentication authentication, Model model) {
       String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
       Utilisateur utilisateur = utilisateurRepository.findByMail(((UserDetails) authentication.getPrincipal()).getUsername());
       Recruteur recruteur = recruteurRepository.findById(utilisateur.getId()).get();
       model.addAttribute("recruteur", recruteur);
        return "Profil_Recruteur";
    }

    @GetMapping("/candidature/{cle}")
    public String getCandidatureByCle(String cle, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Utilisateur utilisateur = utilisateurRepository.findByMail(userDetails.getUsername());
        ChercheurdEmploi ch=chercheurdEmploiRepository.findById(utilisateur.getId()).get();
        List<Candidature> candidatures = candidatureRepository.findByOff_TitreContainsIgnoreCaseOrOff_DescriptionContainsIgnoreCaseAndChe_IdAllIgnoreCase(cle, cle, utilisateur.getId(),Sort.by("dateCandidature").descending());
        model.addAttribute("countofferpostuler", countofferpostuler(authentication));
        model.addAttribute("ListCandidatures", candidatures);
        return "Dashboard-Employer";
    }

    @GetMapping("/candidature/delete/{id}")
    public String deletecandidature(@PathVariable int id,Authentication authentication) {
        String userId = ((UserDetails) authentication.getPrincipal()).getUsername();
        Utilisateur utilisateur = utilisateurRepository.findByMail(((UserDetails) authentication.getPrincipal()).getUsername());
        ChercheurdEmploi chercheurdEmploi = chercheurdEmploiRepository.findById(utilisateur.getId()).get();
        CandidatureId candidatureId=new CandidatureId();
        candidatureId.setCheId(chercheurdEmploi.getId());
        candidatureId.setOffId(id);
        candidatureService.deleteCandidature(candidatureId);
        return "redirect:/to_dashbord";
    }

    @GetMapping("/Postuler")
    public String showPostuler(Model model)
    {
        model.addAttribute("ListeOffres", getalloffres());
        return "Postuler";
    }
    @GetMapping("/offres_List")
    public String showOffres(Model model)
    {
        model.addAttribute("ListeOffres", getalloffres());
        return "offres_List";
    }

    @GetMapping("/contact")
    public String contact()
    {
        return "contact";
    }

    @GetMapping("/service")
    public String service()
    {
        return "service";
    }
        @Autowired
        EmailService emailService;
    @GetMapping("/contact/mail")
    public String sendMail(@RequestParam(name = "email") String email,@RequestParam(name = "subject") String subject,@RequestParam(name = "message") String message,Model model)
    {
        if(emailService.sendEmail(email,subject,message))
            model.addAttribute("msg","Votre message a été envoyé avec succès");
        else
            model.addAttribute("msg","Erreur lors de l'envoi du message");
        return "contact";
    }


    @GetMapping("/Postuler/offres/filter")
    public String getoffresfilter(@RequestParam(name = "cle") String cle,@RequestParam(name="ville") String ville,Model model){
        List<Offre> offres = offreRepository.findOffresByTitreOrDescriptionoAndLieu(cle,cle,ville);
        model.addAttribute("ListeOffres", offres);
        return "Postuler";
    }

    @GetMapping("/offres/filter")
    public String getoffresfilterAdmin(@RequestParam(name = "cle") String cle,@RequestParam(name="ville") String ville,Model model){
        List<Offre> offres = offreRepository.findOffresByTitreOrDescriptionoAndLieu(cle,cle,ville);
        model.addAttribute("ListeOffres", offres);
        return "offres_List";
    }

    @GetMapping("/offre/show/{id}")
    public String showOffre(@PathVariable int id, Model model) {
        Offre offre = offreRepository.findById(id).get();
        long nbr_candidats= candidatureRepository.countDistinctById_OffId(id);
        model.addAttribute("offre", offre);
        model.addAttribute("nbr_candidats", nbr_candidats);
        return "SelectOffre";
    }

    @GetMapping("/offre/fiche/{id}")
    public String showOffreDetails(@PathVariable int id, Model model) {
        Offre offre = offreRepository.findById(id).get();
        long nbr_candidats= candidatureRepository.countDistinctById_OffId(id);
        model.addAttribute("offre", offre);
        model.addAttribute("nbr_candidats", nbr_candidats);
        return "Offres_Details";
    }

    @GetMapping("/offre/show/error/{id}")
    public String showOffreError(@PathVariable int id,Model model){
        System.out.println(id);
        Offre offre = offreRepository.findById(id).get();
        long nbr_candidats= candidatureRepository.countDistinctById_OffId(id);
        model.addAttribute("nbr_candidats", nbr_candidats);
        model.addAttribute("error","Vous avez déja postuler à cette offre !!");
        model.addAttribute("offre", offre);
        return "SelectOffre";
    }


    @PostMapping("/candidature/add")
    public String addCandidature(Authentication authentication,@RequestParam(name = "id") int id,@RequestParam(name = "lettre") String motivation,Model model)
    {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Utilisateur utilisateur = utilisateurRepository.findByMail(userDetails.getUsername());
        ChercheurdEmploi ch = chercheurdEmploiRepository.findById(utilisateur.getId()).get();
        CandidatureId candidatureId = new CandidatureId();
        candidatureId.setCheId(ch.getId());
        candidatureId.setOffId(id);
        if(candidatureRepository.existsById(candidatureId))
        {
            return "redirect:/offre/show/error/"+id;
        }
        else{
        Candidature candidature = new Candidature();
        candidature.setDateCandidature(Instant.now());
        candidature.setChe(ch);
        candidature.setOff(offreRepository.findById(id).get());
        candidature.setLettreMotivation(motivation);
        candidature.setId(candidatureId);
        candidatureService.saveCandidature(candidature);
            return "redirect:/to_dashbord";
        }

    }

    public List<Offre> getalloffres()
    {
        return  (List<Offre>) offreRepository.findAllByOrderByDatePublicationDesc(Sort.by("datePublication").descending());
    }

    @GetMapping("/Recruteurs")
    public String showRecruteurs(Model model)
    {
        model.addAttribute("ListeRecruteurs",  recruteurRepository.findAll());
        model.addAttribute("nbr_recruteurs",recruteurRepository.count());
        return "Recruteurs";
    }

    @GetMapping("/Recruteurs_Admin")
    public String showRecruteursAdmin(Model model)
    {
        model.addAttribute("ListeRecruteurs",  recruteurRepository.findAll());
        model.addAttribute("nbr_recruteurs",recruteurRepository.count());
        return "Recruteurs_admin";
    }

    @PostMapping("/emp/edit")
    public String editprofilemployer(@RequestParam("cvs") MultipartFile file,Model model,@RequestParam String nom,@RequestParam String prenom)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Utilisateur utilisateur = utilisateurRepository.findByMail(userDetails.getUsername());
        ChercheurdEmploi ch = chercheurdEmploiRepository.findById(utilisateur.getId()).get();
        try {
            String fileName ="C:\\Users\\abiid\\Documents\\cvs\\"+ ch.getCv();
            System.out.println(ch.getCv());
            String fileReturn=loginController.handleFileUpload(file);
            if(fileReturn.equals("vide")||fileReturn.equals("extention") ||fileReturn.equals("size")) {
                if (fileReturn.equals("vide"))
                    model.addAttribute("ErreurCv", "Cv ne doit pas etre vide");
                else if (fileReturn.equals("extention"))
                    model.addAttribute("ErreurCv", "Cv doit etre en format pdf");
                else if (fileReturn.equals("size"))
                    model.addAttribute("ErreurCv", "Cv ne doit pas depasser 5 Mo");

                model.addAttribute("user",utilisateur);
                model.addAttribute("rec",ch);
                return "/ProfilEmployer";
            }
            else {
                ch.setCv(loginController.handleFileUpload(file));
                ch.setNom(nom);
                ch.setPrenom(prenom);
                deleteFile(fileName);
                chercheurdEmploiRepository.save(ch);
                model.addAttribute("Succes", "Les donnes sont bien modifier");
                model.addAttribute("user",utilisateur);
                ChercheurdEmploi ch2 = chercheurdEmploiRepository.findById(utilisateur.getId()).get();
                model.addAttribute("rec",ch2);
                return "/Profilemployer";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/to_dashbord";
        }
    }
    public void deleteFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.deleteIfExists(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/Employer/delete/{id}")
    public String deletech(@PathVariable int id) {
        utilisateurRepository.deleteById(id);
        chercheurEmploiService.deleteChercheurEmploi(id);
        return "redirect:/Employeres";
    }

    @GetMapping("/Recruteur/delete/{id}")
    public String deleteRecr(@PathVariable int id) {
        utilisateurRepository.deleteById(id);
        recruteurService.deleteRecruteur(id);
        return "redirect:/Recruteurs_Admin";
    }


}
