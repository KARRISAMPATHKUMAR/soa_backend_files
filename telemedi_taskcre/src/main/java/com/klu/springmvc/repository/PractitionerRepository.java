package com.klu.springmvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.klu.springmvc.model.Practitioner;

@Repository
public interface PractitionerRepository
        extends JpaRepository<Practitioner, Integer> {
}