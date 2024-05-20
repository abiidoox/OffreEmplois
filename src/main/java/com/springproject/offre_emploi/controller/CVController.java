package com.springproject.offre_emploi.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class CVController {

    private static final String CV_FOLDER = "C:\\Users\\abiid\\Documents\\cvs";

    @GetMapping("/cv/{cvName}")
    public ResponseEntity<Resource> downloadCV(@PathVariable("cvName") String cvName) throws IOException {
        if (StringUtils.isEmpty(cvName)) {
            // Handle case when cvName is empty or not provided
            return ResponseEntity.badRequest().build();
        }
        // Create the file path based on the CV name
        Path filePath = Paths.get(CV_FOLDER, cvName);

        // Check if the file exists
        if (Files.exists(filePath)) {
            // Load the file as a Resource
            Resource resource = new FileSystemResource(filePath);

            // Set the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.inline().filename(cvName).build());

            // Return the file as a response
            return ResponseEntity.ok().headers(headers).body(resource);
        } else {
            // Handle case when the file does not exist
            return ResponseEntity.notFound().build();
        }
    }
}
