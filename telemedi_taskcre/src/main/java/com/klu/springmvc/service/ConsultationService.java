package com.klu.springmvc.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.springmvc.model.Consultation;
import com.klu.springmvc.repository.ConsultationRepository;

@Service
public class ConsultationService {

    @Autowired
    private ConsultationRepository repo;

    @Autowired
    private JWTService jwtService;


    public Object createConsultation(
            Consultation consultation,
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


            consultation.setPatientId(patientId);


            if (consultation.getConsultationStatus()
                    == null) {

                consultation.setConsultationStatus(
                        "COMPLETED"
                );
            }


            repo.save(consultation);


            response.put("code", 200);
            response.put(
                    "message",
                    "Consultation saved successfully"
            );
            response.put(
                    "consultation",
                    consultation
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


    public Object getConsultation(
            Integer appointmentId,
            String token) {

        Map<String, Object> response =
                new HashMap<>();

        try {

            jwtService.validateJWT(token);


            Consultation consultation =
                    repo.findByAppointmentId(
                            appointmentId
                    ).orElse(null);


            if (consultation == null) {

                response.put("code", 404);
                response.put(
                        "message",
                        "Consultation not found"
                );

                return response;
            }


            response.put("code", 200);
            response.put(
                    "consultation",
                    consultation
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