package com.springproject.offre_emploi;

import com.springproject.offre_emploi.controller.IndexController;
import com.springproject.offre_emploi.services.JobService;
import com.springproject.offre_emploi.services.JobService2;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


//@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "com.springproject.offre_emploi.*")
public class OffreEmploiApplication {

	public static ChromeDriver chromeDriver;

	public static void main(String[] args) {
		 chromeDriver=IndexController.initialize();
		JobService.initialize(chromeDriver);
		JobService2.initialize(chromeDriver);

		SpringApplication.run(OffreEmploiApplication.class, args);
	}

	// Hook to quit ChromeDriver when the application is shutting down

}
