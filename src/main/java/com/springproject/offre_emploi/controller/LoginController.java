package com.springproject.offre_emploi.controller;


import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.springproject.offre_emploi.Beans.ChercheurdEmploi;
import com.springproject.offre_emploi.Beans.Recruteur;
import com.springproject.offre_emploi.Beans.Utilisateur;
import com.springproject.offre_emploi.Repository.ChercheurdEmploiRepository;
import com.springproject.offre_emploi.Repository.RecruteurRepository;
import com.springproject.offre_emploi.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class LoginController {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private RecruteurRepository recruteurRepository;

    @Autowired
    private ChercheurdEmploiRepository chercheurdEmploiRepository;

    @GetMapping("/login")
    public String showLoginForm(Authentication authentication) {
        if(authentication==null)
            return "Login";
        else
            return "redirect:/to_dashbord";

    }

    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "redirect:/access-denied.html";
    }


    @GetMapping("/RegistreRecruteurY")
    public String registrationForm(Model model) {
        Utilisateur user = new Utilisateur();
        model.addAttribute("user", user);
        Recruteur rec =new Recruteur();
        model.addAttribute("rec",rec);
        return "RegistreRecruteur";
    }

    @PostMapping("/RegistreRecruteurX")
    public String registration(
            @ModelAttribute("user") Utilisateur userDto,
            @ModelAttribute("rec") Recruteur recDto,
            BindingResult result,
            Model model) {
        Utilisateur existingUser = utilisateurService.findUserByEmail(userDto.getMail());

        if (existingUser != null || !userDto.getMail().contains("@") || userDto.getPassword().length()<8 || userDto.getPassword().length()>20 || recDto.getEntreprise()==null || recDto.getLieu()=="Ville")
        {
            if (existingUser != null)
                model.addAttribute("ErreurMail","Email déja utlisé");
            if (!userDto.getMail().contains("@"))
                model.addAttribute("ErreurMail","Email Non Valide");
            if (userDto.getPassword().length()<8 || userDto.getPassword().length()>20)
                model.addAttribute("ErreurPassword","Mot de passe doit etre entre 8 et 20 caractéres");
            if (recDto.getEntreprise()==null)
                model.addAttribute("ErreurEntreprise","Entreprise ne doit pas etre vide");
            if (recDto.getLieu()=="Ville")
                model.addAttribute("ErreurLieu","Lieu ne doit pas etre vide");
            model.addAttribute("user", userDto);
            model.addAttribute("rec",recDto);
            return "RegistreRecruteur";
        }
        userDto.setType("recruteur");
        utilisateurService.saveUtilisateur(userDto);
        userDto=utilisateurService.findUserByEmail(userDto.getMail());
        recDto.setUtilisateurs(userDto);
        recruteurRepository.save(recDto);
        return "redirect:/login";
    }

    @GetMapping("/RegistreEmployeY")
    public String registrationFormEmp(Model model) {
        Utilisateur user = new Utilisateur();
        model.addAttribute("user", user);
        ChercheurdEmploi rec =new ChercheurdEmploi();
        model.addAttribute("rec",rec);
        return "RegistreEmploye";
    }

    @PostMapping("/RegistreEmployeX")
    public String registrationEmp(
            @ModelAttribute("user") Utilisateur userDto,
            @ModelAttribute("rec") ChercheurdEmploi recDto,
            @RequestParam("cvs") MultipartFile file,
            BindingResult result,
            Model model) {
        Utilisateur existingUser = utilisateurService.findUserByEmail(userDto.getMail());
        String fileReturn = handleFileUpload(file);
        System.out.println(file.getOriginalFilename());
        if (existingUser != null || !userDto.getMail().contains("@") || userDto.getPassword().length()<8 || userDto.getPassword().length()>20 || recDto.getNom()==""  || recDto.getPrenom()=="" || fileReturn.contains("pdf")==false)
        {
            if (existingUser != null)
                model.addAttribute("ErreurMail","Email déja utlisé");
            if (!userDto.getMail().contains("@"))
                model.addAttribute("ErreurMail","Email Non Valide");
            if (userDto.getPassword().length()<8 || userDto.getPassword().length()>20)
                model.addAttribute("ErreurPassword","Mot de passe doit etre entre 8 et 20 caractéres");
            if (recDto.getNom()=="")
                model.addAttribute("ErreurNom","Nom ne doit pas etre vide");
            if (recDto.getPrenom()=="")
                model.addAttribute("ErreurPrenom","Prenom ne doit pas etre vide");

            if(fileReturn.equals("vide"))
                model.addAttribute("ErreurCv","Cv ne doit pas etre vide");
            else if(fileReturn.equals("extention"))
                model.addAttribute("ErreurCv","Cv doit etre en format pdf");
            else if(fileReturn.equals("size"))
                    model.addAttribute("ErreurCv","Cv ne doit pas depasser 5 Mo");
            model.addAttribute("user", userDto);
            model.addAttribute("rec",recDto);
            return "/RegistreEmploye";
        }
        userDto.setType("Employer");
        utilisateurService.saveUtilisateur(userDto);
        userDto=utilisateurService.findUserByEmail(userDto.getMail());
//        recDto.setId(userDto.getId());
        recDto.setUtilisateurs(userDto);
        recDto.setCv(fileReturn);
        chercheurdEmploiRepository.save(recDto);
        return "redirect:/login";
    }
        public String handleFileUpload(MultipartFile file) {
            // Validate file type
            if (!file.getContentType().equalsIgnoreCase("application/pdf")) {
                // Handle invalid file type error
                return "extention"; // Redirect to an error page or return a response accordingly
            }
            if (file.getSize() > 5 * 1024 * 1024) { // 5MB in bytes
                // Handle file size exceeded error
                return "size"; // Redirect to an error page or return a response accordingly
            }
            if (file.getSize() ==0) { // 0MB in bytes
                // Handle file size exceeded error
                return "vide"; // Redirect to an error page or return a response accordingly
            }
            // Generate a unique filename using the current date and original file extension
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String currentDate = dateFormat.format(new Date());
            String uniqueFilename =  currentDate + "_" + originalFilename;

            // Save the file to the specified directory
            String uploadDir = "C:\\Users\\abiid\\Documents\\cvs";
            Path uploadPath = Path.of(uploadDir);

            try {
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                Path filePath = uploadPath.resolve(uniqueFilename);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                return uniqueFilename;
            } catch (IOException e) {
                // Handle file storage error
                return "uploadError"; // Redirect to an error page or return a response accordingly
            }

    }

    @GetMapping("/toProfilEmployer")
    public String toProfilEmployer(Model model, Authentication authentication) {
        Utilisateur user = utilisateurService.findUserByEmail(authentication.getName());
        model.addAttribute("user",user);
        ChercheurdEmploi rec = chercheurdEmploiRepository.findById(user.getId()).get();
        model.addAttribute("rec",rec);
        return "ProfilEmployer";
    }

}
