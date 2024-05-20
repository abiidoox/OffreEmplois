package com.springproject.offre_emploi.services;


import com.springproject.offre_emploi.Beans.Link;
import com.springproject.offre_emploi.Beans.Offre;
import com.springproject.offre_emploi.Beans.Recruteur;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Data
public class JobService2 {
    private static ChromeDriver driver;
    private Map<Link, Offre> objectMap=new HashMap<>();



    public JobService2() {
        objectMap = new HashMap<>();
    }

    public static void initialize(ChromeDriver chromeDriver){
        driver = chromeDriver;
    }

    public void pagination(int page,String test) throws InterruptedException {
        String nextPageUrl="https://www.rekrute.com/offres.html?p="+page+"&s=1&o=1&query="+test+"&keyword="+test+"&st=d&jobLocation=RK";
        driver.get(nextPageUrl);
       /* WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10000)); // Attente maximale de 10 secondes
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("post-data")));*/
        getScrap();
    }
    public void getScrap() throws InterruptedException {

        WebElement jobCard = driver.findElement(By.id("post-data"));
        List<WebElement> jobCardWitha = jobCard.findElements(By.className("post-id"));
        int totcount = jobCardWitha.size()>5?5:jobCardWitha.size();
        Offre iJob ;        Recruteur iJob2 ;        Link iJob3 ;        WebElement jobTitle;        String jobTitleCompany;
        String[] parts;        String Titre;        String Location;        WebElement job = null;        WebElement jobLink;
        String pageUrl;        String jobLinkUrl;        WebElement jobDate;        WebElement jobDescription;        String jobDescriptionUrl;
        String jobDateUrl;        DateTimeFormatter formatter;        LocalDate localDate;        Instant instant;
        for (int i = 0; i < totcount; i++) {
             iJob = new Offre();
             iJob2 = new Recruteur();
             iJob3 = new Link();
             jobTitle = jobCardWitha.get(i).findElement(By.className("titreJob"));
            jobTitleCompany = jobTitle.getText();
            parts = jobTitleCompany.split("\\|");
            Titre = parts[0];
            Location = parts[1];
            iJob.setTitre(Titre.replace(",", " ").replace("\n", " "));
            iJob2.setLieu(Location.replace(",", " ").replace("\n", " "));
            try {
                job = jobCardWitha.get(i).findElement(By.className("photo"));
                 pageUrl = job.getAttribute("alt");
                if (pageUrl == null || pageUrl.trim().isEmpty() || pageUrl.trim().equals("")) {
                    iJob2.setEntreprise("Aucne information");
                } else {
                    iJob2.setEntreprise(pageUrl);
                }
            } catch (NoSuchElementException e) {
                iJob2.setEntreprise("Aucune information");
            }
            jobLink = jobCardWitha.get(i).findElement(By.className("titreJob"));
             jobLinkUrl = jobLink.getAttribute("href");
            iJob3.setLink(jobLinkUrl);
            iJob3.setSource("Rekrute");
             jobDescription = jobCardWitha.get(i).findElement(By.xpath("./div/div[2]/div/div/div[2]/span"));
             jobDescriptionUrl= jobDescription.getText();
            iJob.setDescription(jobDescriptionUrl);

            // To get Date
             jobDate = jobCardWitha.get(i).findElement(By.xpath("./div/div[2]/div/div/em/span[1]"));
             jobDateUrl = jobDate.getText();
             formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
             localDate = LocalDate.parse(jobDateUrl, formatter);
             instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
            iJob.setDatePublication(instant);
            // Set the object
            iJob.setRec(iJob2);
            objectMap.put(iJob3,iJob);
        }
    }
    public Map<Link, Offre> printObjects(String test ,int page) throws InterruptedException {
        objectMap.clear();
        pagination(page,test);
        return objectMap;
    }

    public static int getNumberOfPages(String str) {
        WebElement pagination = driver.findElement(By.className("jobs"));
        String text = pagination.getText().split("sur")[1].trim();
        int extractedNumber = Integer.parseInt(text);
        return extractedNumber;
    }
}


