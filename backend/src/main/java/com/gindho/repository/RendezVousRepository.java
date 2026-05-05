package com.gindho.repository;

import com.gindho.model.RendezVous;
import com.gindho.model.RendezVous.StatutRDV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {
    List<RendezVous> findByMedecinIdAndDateHeureDebutBetween(Long medecinId, LocalDateTime debut, LocalDateTime fin);
    
    List<RendezVous> findByMedecinIdAndStatutNot(Long medecinId, StatutRDV statut);
    
    List<RendezVous> findByPatientId(Long patientId);
    
    List<RendezVous> findByMedecinId(Long medecinId);
    
    Optional<RendezVous> findByMedecinIdAndDateHeureDebutBetweenAndStatutNot(
        Long medecinId, LocalDateTime debut, LocalDateTime fin, StatutRDV statut);
}
