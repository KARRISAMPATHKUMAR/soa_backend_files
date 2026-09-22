package com.klu.springmvc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.springmvc.model.Appointment;
import com.klu.springmvc.model.Practitioner;
import com.klu.springmvc.repository.AppointmentRepository;
import com.klu.springmvc.repository.PractitionerRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepo;

    @Autowired
    private PractitionerRepository practitionerRepo;

    @Autowired
    private JWTService jwtService;


    // =====================================================
    // BOOK APPOINTMENT
    // =====================================================

    public Object bookAppointment(
            Appointment appointment,
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


            // Patient ID comes from JWT.
            // Do not trust frontend patientId.
            appointment.setPatientId(patientId);


            if (appointment.getPractitionerId()
                    == null) {

                response.put("code", 400);
                response.put(
                        "message",
                        "Practitioner is required"
                );

                return response;
            }


            Practitioner practitioner =
                    practitionerRepo.findById(
                            appointment.getPractitionerId()
                    ).orElse(null);


            if (practitioner == null) {

                response.put("code", 404);
                response.put(
                        "message",
                        "Practitioner not found"
                );

                return response;
            }


            if ("UNAVAILABLE".equalsIgnoreCase(
                    practitioner.getAvailabilityStatus())) {

                response.put("code", 400);
                response.put(
                        "message",
                        "Practitioner is not available"
                );

                return response;
            }


            if (appointment.getAppointmentStatus()
                    == null) {

                appointment.setAppointmentStatus(
                        "CONFIRMED");
            }


            appointmentRepo.save(appointment);


            response.put("code", 200);
            response.put(
                    "message",
                    "Appointment booked successfully"
            );
            response.put(
                    "appointment",
                    appointment
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
    // GET PATIENT APPOINTMENTS
    // =====================================================

    public Object getPatientAppointments(
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


            List<Appointment> appointments =
                    appointmentRepo.findByPatientId(
                            patientId
                    );


            /*
             * We create response objects containing
             * practitioner name and specialization.
             *
             * This matches the React frontend.
             */
            List<Map<String, Object>> result =
                    new ArrayList<>();


            for (Appointment appointment :
                    appointments) {

                Map<String, Object> item =
                        new HashMap<>();


                item.put(
                        "appointmentId",
                        appointment.getAppointmentId()
                );

                item.put(
                        "patientId",
                        appointment.getPatientId()
                );

                item.put(
                        "practitionerId",
                        appointment.getPractitionerId()
                );

                item.put(
                        "appointmentTime",
                        appointment.getAppointmentTime()
                );

                item.put(
                        "appointmentStatus",
                        appointment.getAppointmentStatus()
                );


                Practitioner practitioner =
                        practitionerRepo.findById(
                                appointment.getPractitionerId()
                        ).orElse(null);


                if (practitioner != null) {

                    item.put(
                            "practitionerName",
                            practitioner.getName()
                    );

                    item.put(
                            "specialization",
                            practitioner.getSpecialization()
                    );

                    item.put(
                            "consultationFee",
                            practitioner.getConsultationFee()
                    );
                }


                result.add(item);
            }


            response.put("code", 200);
            response.put(
                    "appointments",
                    result
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
    // CANCEL APPOINTMENT
    // =====================================================

    public Object cancelAppointment(
            Integer id,
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


            Appointment appointment =
                    appointmentRepo.findById(id)
                            .orElse(null);


            if (appointment == null) {

                response.put("code", 404);
                response.put(
                        "message",
                        "Appointment not found"
                );

                return response;
            }


            // Patient can cancel only their own appointment
            if (!appointment.getPatientId()
                    .equals(patientId)) {

                response.put("code", 403);
                response.put(
                        "message",
                        "You cannot cancel this appointment"
                );

                return response;
            }


            appointment.setAppointmentStatus(
                    "CANCELLED"
            );


            appointmentRepo.save(appointment);


            response.put("code", 200);
            response.put(
                    "message",
                    "Appointment cancelled successfully"
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