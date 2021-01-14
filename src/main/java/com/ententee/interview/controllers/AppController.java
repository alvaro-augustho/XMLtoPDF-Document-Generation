package com.ententee.interview.controllers;

import com.ententee.interview.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *      This is the only controller for this application
 *      It receives the parameters from user and manages the document generation
 */
@RestController
public class AppController {

    private static final Logger LOGGER = Logger.getLogger(AppController.class.getName());

    private static final String PDF_FILE_NAME = "treatment-documentation.pdf";

    @Autowired
    DocumentService documentService;

    @PostMapping("/treatment/documentation")
    public @ResponseBody
    ResponseEntity<byte[]> treatmentDocumentation(@RequestParam Map<String, String> body) {

        LOGGER.log(Level.INFO, "[AppController#treatmentDocumentation] Received POST body: {0}", body);

        byte[] pdf = documentService.generateDocument(body);

        LOGGER.log(Level.INFO, "[AppController#treatmentDocumentation] PDF file is ready");

        LOGGER.log(Level.INFO, "[AppController#treatmentDocumentation] Preparing response body");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Here you have to set the actual filename of your pdf
        String filename = PDF_FILE_NAME;
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(pdf, headers, HttpStatus.OK);

        LOGGER.log(Level.INFO, "[AppController#treatmentDocumentation] Returning response body");

        return response;
    }

}
