package com.springproject.offre_emploi.services;

import com.springproject.offre_emploi.Beans.Link;
import com.springproject.offre_emploi.Beans.Offre;
import com.springproject.offre_emploi.Beans.Recruteur;
import com.springproject.offre_emploi.Repository.OffreRepository;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Data
public class JobServiceBase {
    private Map<Link, Offre> objectMap=new HashMap<>();
    @Autowired
    private OffreRepository offreRepository;

    public Map<Link, Offre> printObjects(String test, int page) throws InterruptedException {
        objectMap.clear();
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Offre> offrePage = offreRepository.findByTitreContainsIgnoreCaseOrDescriptionContainsIgnoreCaseAndRec_LieuIgnoreCase(test, test, pageable);
        List<Offre> offres = offrePage.getContent();
        Link link ;
        for (Offre offre : offres) {
            link = new Link();
            link.setLink("http://localhost:8080/offre/show/" + offre.getId());
            link.setSource("HireLink");
            objectMap.put(link, offre);
        }
        return objectMap;
    }



}









