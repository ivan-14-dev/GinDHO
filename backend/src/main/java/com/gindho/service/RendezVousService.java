package com.gindho.service;

import com.gindho.dto.RendezVousDto;
import com.gindho.model.*;
import com.gindho.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RendezVousService {

    private final RendezVousRepository rendezVousRepository;
    private final MedecinRepository medecinRepository;
    private final PatientRepository patientRepository;

    public RendezVousDto prendreRDV(RendezVousDto dto) {
        Medecin medecin = medecinRepository.findById(dto.getMedecinId())
                .orElseThrow(() -> new RuntimeException("Medecin not found"));
        Patient patient = patientRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        if (verifierConflits(dto.getMedecinId(), dto.getDateHeureDebut(), dto.getDateHeureFin())) {
            throw new RuntimeException("Conflit d'horaire détecté");
        }

        RendezVous rdv = RendezVous.builder()
                .dateHeureDebut(dto.getDateHeureDebut())
                .dateHeureFin(dto.getDateHeureFin())
                .statut(RendezVous.StatutRDV.CONFIRME)
                .motif(dto.getMotif())
                .notes(dto.getNotes())
                .patient(patient)
                .medecin(medecin)
                .build();

        rdv = rendezVousRepository.save(rdv);
        return convertToDto(rdv);
    }

    public void annuler(Long id, String motif) {
        RendezVous rdv = rendezVousRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rendez-vous not found"));
        rdv.setStatut(RendezVous.StatutRDV.ANNULE);
        rdv.setNotes(rdv.getNotes() != null ? rdv.getNotes() + "\nAnnulé: " + motif : "Annulé: " + motif);
        rendezVousRepository.save(rdv);
    }

    public boolean verifierConflits(Long medecinId, LocalDateTime debut, LocalDateTime fin) {
        List<RendezVous> conflits = rendezVousRepository
                .findByMedecinIdAndDateHeureDebutBetweenAndStatutNot(
                        medecinId, debut, fin, RendezVous.StatutRDV.ANNULE);
        return !conflits.isEmpty();
    }

    public List<RendezVousDto> getRDVsPatient(Long patientId) {
        return rendezVousRepository.findByPatientId(patientId).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<RendezVousDto> getRDVsMedecin(Long medecinId, LocalDateTime date) {
        LocalDateTime startOfDay = date.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = date.toLocalDate().atTime(23, 59, 59);
        return rendezVousRepository
                .findByMedecinIdAndDateHeureDebutBetween(medecinId, startOfDay, endOfDay).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RendezVousDto convertToDto(RendezVous rdv) {
        return RendezVousDto.builder()
                .id(rdv.getId())
                .dateHeureDebut(rdv.getDateHeureDebut())
                .dateHeureFin(rdv.getDateHeureFin())
                .statut(rdv.getStatut())
                .motif(rdv.getMotif())
                .notes(rdv.getNotes())
                .patientId(rdv.getPatient().getId())
                .medecinId(rdv.getMedecin().getId())
                .patientNom(rdv.getPatient().getNom() + " " + rdv.getPatient().getPrenom())
                .medecinNom(rdv.getMedecin().getNom() + " " + rdv.getMedecin().getPrenom())
                .build();
    }
}
