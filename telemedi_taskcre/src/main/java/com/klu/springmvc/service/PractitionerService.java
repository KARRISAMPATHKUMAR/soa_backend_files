package com.klu.springmvc.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.springmvc.model.Practitioner;
import com.klu.springmvc.repository.PractitionerRepository;

@Service
public class PractitionerService {

    @Autowired
    private PractitionerRepository repo;

    @Autowired
    private JWTService jwtService;


    // =====================================================
    // GET ALL PRACTITIONERS
    // =====================================================

    public Object getAllPractitioners(String token) {

        Map<String, Object> response =
                new HashMap<>();

        try {

            jwtService.validateJWT(token);

            List<Practitioner> practitioners =
                    repo.findAll();


            response.put("code", 200);
            response.put(
                    "practitioners",
                    practitioners
            );

        } catch (Exception e) {

            response.put("code", 401);
            response.put(
                    "message",
                    "Invalid or expired token"
            );
        }

        return response;
    }


    // =====================================================
    // CREATE PRACTITIONER
    // =====================================================

    public Object createPractitioner(
            Practitioner practitioner,
            String token) {

        Map<String, Object> response =
                new HashMap<>();

        try {

            jwtService.validateJWT(token);

            if (practitioner.getAvailabilityStatus()
                    == null) {

                practitioner.setAvailabilityStatus(
                        "AVAILABLE");
            }


            repo.save(practitioner);


            response.put("code", 200);
            response.put(
                    "message",
                    "Practitioner created successfully"
            );
            response.put(
                    "practitioner",
                    practitioner
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
    // UPDATE PRACTITIONER
    // =====================================================

    public Object updatePractitioner(
            Integer id,
            Practitioner practitioner,
            String token) {

        Map<String, Object> response =
                new HashMap<>();

        try {

            jwtService.validateJWT(token);


            Practitioner existing =
                    repo.findById(id).orElse(null);


            if (existing == null) {

                response.put("code", 404);
                response.put(
                        "message",
                        "Practitioner not found"
                );

                return response;
            }


            existing.setName(
                    practitioner.getName());

            existing.setSpecialization(
                    practitioner.getSpecialization());

            existing.setConsultationFee(
                    practitioner.getConsultationFee());

            existing.setAvailabilityStatus(
                    practitioner.getAvailabilityStatus());


            repo.save(existing);


            response.put("code", 200);
            response.put(
                    "message",
                    "Practitioner updated successfully"
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
    // DELETE PRACTITIONER
    // =====================================================

    public Object deletePractitioner(
            Integer id,
            String token) {

        Map<String, Object> response =
                new HashMap<>();

        try {

            jwtService.validateJWT(token);


            if (!repo.existsById(id)) {

                response.put("code", 404);
                response.put(
                        "message",
                        "Practitioner not found"
                );

                return response;
            }


            repo.deleteById(id);


            response.put("code", 200);
            response.put(
                    "message",
                    "Practitioner deleted successfully"
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