package com.klu.springmvc;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class FallbackController {

    @GetMapping("/fallback/patient")
    public String patientFallback() {
        return "Patient Service is currently unavailable. Please try again later.";
    }


    @GetMapping("/fallback/appointment")
    public String appointmentFallback() {
        return "Appointment Service is currently unavailable. Please try again later.";
    }
}