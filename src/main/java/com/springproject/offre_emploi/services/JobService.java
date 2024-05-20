package com.springproject.offre_emploi.services;

import com.springproject.offre_emploi.Beans.Link;
import com.springproject.offre_emploi.Beans.Offre;
import com.springproject.offre_emploi.Beans.Recruteur;
import lombok.Data;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Data
public class JobService {
    private static   ChromeDriver driver;
    private Map<Link, Offre> objectMap;

    public  JobService(){
        objectMap = new HashMap<>();
    }

    public static void initialize(ChromeDriver chromeDriver) {
        driver = chromeDriver;
    }
    public void pagination(int page,String JobSearch) throws InterruptedException {
        String nextPageUrl;
        if(page==1){
            nextPageUrl = "https://ma.indeed.com/jobs?q="+JobSearch+"&l=Maroc&sort=date";
        }else {
            String baseUrl = "https://ma.indeed.com/jobs?q"+JobSearch+"=&l=Maroc&sort=date&start=";
            int resultsPerPage = 5; // Nombre de résultats affichés par page
            int startValue = (page - 1) * resultsPerPage;
            nextPageUrl = baseUrl + startValue;
        }
        driver.get(nextPageUrl);
     /*   WebDriverWait wait = new WebDriverWait(driver,Duration.ofMillis(10000)); // Attente maximale de 10 secondes
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("jobsearch-ResultsList")));*/
        getScrap();
    }


    public void getScrap() throws InterruptedException {
        // Check if an alert is present
        WebElement jobCard = driver.findElement(By.className("jobsearch-ResultsList"));
        List<WebElement> jobCardWitha = jobCard.findElements(By.className("cardOutline"));
        Offre iJob ;
        Recruteur iRec ;
        Link iLink ;
        WebElement dateJob;
        Instant currentInstant;
        int numberOfDays;
        String extractedNumber;
        Instant currentInstant2;
        Instant calculatedInstant;
        int nbr=  jobCardWitha.size()>5?5:jobCardWitha.size();
        for (int i = 0; i < nbr; i++) {

            try {
                WebElement closeButton = driver.findElement(By.className("icl-CloseButton"));
                closeButton.click();
            } catch (NoSuchElementException e) {
            }
            try {
                WebElement closeButton2 = driver.findElement(By.xpath("//button[contains(@aria-label, 'fermer')]"));
                closeButton2.click();
            } catch (NoSuchElementException e) {
            }

            iJob = new Offre();
            iRec = new Recruteur();
            iLink = new Link();
            iLink.setSource("Indeed");
            try {
                dateJob     = jobCardWitha.get(i).findElement(By.className("date"));
            }
            catch (Exception e)
            {
                continue;
            }
            String dateText = dateJob.getText();
            if(dateText.equals("Posted\nPosted 30+ days ago") || dateText.equals(".")|| dateText.equals("")||dateText.equals("Hiring ongoing") || dateText.equals("Posted\nOffre publiée il y a plus de 30 jours") || dateText.equals("Recrutement régulier")){
                continue;
            }
            else{
                WebElement jobTitle = jobCardWitha.get(i).findElement(By.className("jcs-JobTitle")).findElement(By.tagName("span"));

                iJob.setTitre(jobTitle.getText());
                List<WebElement> companyName = jobCardWitha.get(i).findElements(By.className("companyName"));
                try{
                    if(!companyName.isEmpty())
                    {
                        WebElement CompanyName = companyName.get(0);
                        iRec.setEntreprise(CompanyName.getText());
                    }
                    else
                    {
                        iRec.setEntreprise("");
                    }
                } catch (NoSuchElementException e) {
                    iRec.setEntreprise("");
                }

                // To get Company Location
                WebElement companyLocation = jobCardWitha.get(i).findElement(By.className("companyLocation"));
                iRec.setLieu(companyLocation.getText());

                // To get job Description
                WebElement jobDesciption = jobCardWitha.get(i).findElement(By.className("job-snippet"));
                iJob.setDescription(jobDesciption.getText().replace(".", "\n"));

                // To get job Link
                WebElement L = jobCardWitha.get(i).findElement(By.className("jcs-JobTitle"));
                iLink.setLink(L.getAttribute("href"));

                // To get job date

                if(dateText.equals("Posted\nPublié à l'instant") || dateText.equals("Posted\nAujourd'hui")){
                    currentInstant = Instant.now();
                    iJob.setDatePublication(currentInstant);
                }
                else{
                    extractedNumber = dateText.replaceAll("\\D+", ""); // Filter out non-digit characters
                    numberOfDays = Integer.parseInt(extractedNumber);
                    currentInstant2 = Instant.now();
                    calculatedInstant = currentInstant2.minus(Duration.ofDays(numberOfDays));
                    iJob.setDatePublication(calculatedInstant);
                }
            }
            iJob.setRec(iRec);
            objectMap.put(iLink,iJob);
        }
    }

    public Map<Link, Offre> printObjects(String test,int page) throws InterruptedException {
        objectMap.clear();
        pagination(page,test);
        return objectMap;
    }

    public static int getNumberOfPages(String str) {
        WebElement pagination = driver.findElement(By.className("jobsearch-JobCountAndSortPane-jobCount")).findElement(By.xpath("./span[1]"));
        String text = pagination.getText();
        String extractedNumber = text.split("&nbsp;")[0];
        int numberOfJobs = Integer.parseInt(extractedNumber.replaceAll("\\D+", "")); // Filter out non-digit characters
        return (int) Math.ceil((double) numberOfJobs / 5);
    }

}









