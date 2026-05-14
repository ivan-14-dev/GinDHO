package com.gindho.repository;

import com.gindho.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByNumeroPatient(String numeroPatient);
    Optional<Patient> findByUserEmail(String email);
    
    @Query("SELECT p FROM Patient p JOIN p.user u WHERE u.nom LIKE %:criteres% OR u.prenom LIKE %:criteres% OR p.numeroPatient LIKE %:criteres%")
    org.springframework.data.domain.Page<Patient> findByNomContainingOrPrenomContainingOrNumeroPatientContaining(
            @Param("criteres") String criteres, 
            org.springframework.data.domain.Pageable pageable);
}
