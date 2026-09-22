package com.klu.springmvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.springmvc.model.Prescription;
import com.klu.springmvc.repository.PrescriptionRepository;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository repo;

    @Autowired
    private JWTService jwtService;


    // =====================================================
    // CREATE PRESCRIPTION
    // =====================================================

    public Object createPrescription(
            Prescription prescription,
            String token) {

        Map<String, Object> response =
                new HashMap<>();

        try {

            Map<String, String> user =
                    jwtService.validateJWT(token);


            Integer patientId =
                    Integer.parseInt(
                            user.get("id")
                    );


            prescription.setPatientId(patientId);


            repo.save(prescription);


            response.put("code", 200);
            response.put(
                    "message",
                    "Prescription generated successfully"
            );

            response.put(
                    "prescription",
                    prescription
            );

        } catch (Exception e) {

            response.put("code", 500);
            response.put(
                    "message",
                    e.getMessage()
            );
        }

        return response;
    }


    // =====================================================
    // GET PATIENT PRESCRIPTIONS
    // =====================================================

    public Object getPatientPrescriptions(
            String token) {

        Map<String, Object> response =
                new HashMap<>();

        try {

            Map<String, String> user =
                    jwtService.validateJWT(token);


            Integer patientId =
                    Integer.parseInt(
                            user.get("id")
                    );


            List<Prescription> prescriptions =
                    repo.findByPatientId(patientId);


            response.put("code", 200);
            response.put(
                    "prescriptions",
                    prescriptions
            );

        } catch (Exception e) {

            response.put("code", 500);
            response.put(
                    "message",
                    e.getMessage()
            );
        }

        return response;
    }
}