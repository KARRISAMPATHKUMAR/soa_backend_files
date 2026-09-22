package com.klu.springmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.klu.springmvc.model.Appointment;
import com.klu.springmvc.model.Consultation;
import com.klu.springmvc.model.Prescription;
import com.klu.springmvc.model.Practitioner;
import com.klu.springmvc.service.AppointmentService;
import com.klu.springmvc.service.ConsultationService;
import com.klu.springmvc.service.PrescriptionService;
import com.klu.springmvc.service.PractitionerService;

@RestController
@RequestMapping("/ms3")

public class MS2Controller {

    @Autowired
    private PractitionerService practitionerService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private PrescriptionService prescriptionService;


    // =====================================================
    // TEST
    // =====================================================

    @GetMapping("/test")
    public String test() {
        return "Appointment Service is working";
    }


    // =====================================================
    // PRACTITIONER
    // =====================================================

    @GetMapping("/practitioners")
    public Object getPractitioners(
            @RequestHeader("Token") String token) {

        return practitionerService.getAllPractitioners(token);
    }


    @PostMapping("/practitioners")
    public Object createPractitioner(
            @RequestBody Practitioner practitioner,
            @RequestHeader("Token") String token) {

        return practitionerService.createPractitioner(
                practitioner, token);
    }


    @PutMapping("/practitioners/{id}")
    public Object updatePractitioner(
            @PathVariable Integer id,
            @RequestBody Practitioner practitioner,
            @RequestHeader("Token") String token) {

        return practitionerService.updatePractitioner(
                id, practitioner, token);
    }


    @DeleteMapping("/practitioners/{id}")
    public Object deletePractitioner(
            @PathVariable Integer id,
            @RequestHeader("Token") String token) {

        return practitionerService.deletePractitioner(
                id, token);
    }


    // =====================================================
    // APPOINTMENTS
    // =====================================================

    @PostMapping("/appointments")
    public Object bookAppointment(
            @RequestBody Appointment appointment,
            @RequestHeader("Token") String token) {

        return appointmentService.bookAppointment(
                appointment, token);
    }


    @GetMapping("/appointments/patient")
    public Object getPatientAppointments(
            @RequestHeader("Token") String token) {

        return appointmentService.getPatientAppointments(token);
    }


    @DeleteMapping("/appointments/{id}")
    public Object cancelAppointment(
            @PathVariable Integer id,
            @RequestHeader("Token") String token) {

        return appointmentService.cancelAppointment(
                id, token);
    }


    // =====================================================
    // CONSULTATION
    // =====================================================

    @PostMapping("/consultations")
    public Object createConsultation(
            @RequestBody Consultation consultation,
            @RequestHeader("Token") String token) {

        return consultationService.createConsultation(
                consultation, token);
    }


    @GetMapping("/consultations/{appointmentId}")
    public Object getConsultation(
            @PathVariable Integer appointmentId,
            @RequestHeader("Token") String token) {

        return consultationService.getConsultation(
                appointmentId, token);
    }


    // =====================================================
    // PRESCRIPTION
    // =====================================================

    @PostMapping("/prescriptions")
    public Object createPrescription(
            @RequestBody Prescription prescription,
            @RequestHeader("Token") String token) {

        return prescriptionService.createPrescription(
                prescription, token);
    }


    @GetMapping("/prescriptions/patient")
    public Object getPatientPrescriptions(
            @RequestHeader("Token") String token) {

        return prescriptionService.getPatientPrescriptions(token);
    }
}