package com.gindho.repository;

import com.gindho.model.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    Optional<Medecin> findByNumeroOrdre(String numeroOrdre);
    Optional<Medecin> findByUserEmail(String email);
    List<Medecin> findByDisponibleTrue();
}
