package com.klu.springmvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klu.springmvc.model.Prescription;

@Repository
public interface PrescriptionRepository
        extends JpaRepository<Prescription, Integer> {

    List<Prescription> findByPatientId(Integer patientId);
}