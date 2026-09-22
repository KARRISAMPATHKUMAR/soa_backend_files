package com.klu.springmvc.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klu.springmvc.model.Consultation;

@Repository
public interface ConsultationRepository
        extends JpaRepository<Consultation, Integer> {

    Optional<Consultation> findByAppointmentId(
            Integer appointmentId);
}