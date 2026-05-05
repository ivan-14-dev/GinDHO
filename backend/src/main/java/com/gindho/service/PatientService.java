package com.gindho.service;

import com.gindho.dto.PatientDto;
import com.gindho.model.Patient;
import com.gindho.model.User;
import com.gindho.repository.PatientRepository;
import com.gindho.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public PatientDto creer(PatientDto dto) {
        Patient patient = convertToEntity(dto);
        
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            patient.setUser(user);
        }
        
        patient = patientRepository.save(patient);
        return convertToDto(patient);
    }

    public Page<Patient> rechercher(String criteres, Pageable pageable) {
        if (criteres != null && !criteres.isEmpty()) {
            return patientRepository.findByNomContainingOrPrenomContainingOrNumeroPatientContaining(
                    criteres, criteres, criteres, pageable);
        }
        return patientRepository.findAll(pageable);
    }

    public PatientDto mettreAJour(Long id, PatientDto dto) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        
        patient.setNumeroPatient(dto.getNumeroPatient());
        patient.setDateNaissance(dto.getDateNaissance());
        patient.setSexe(dto.getSexe());
        patient.setTelephone(dto.getTelephone());
        patient.setGroupeSanguin(dto.getGroupeSanguin());
        patient.setAllergies(dto.getAllergies());
        patient.setAdresse(dto.getAdresse());
        
        patient = patientRepository.save(patient);
        return convertToDto(patient);
    }

    public PatientDto getDossier(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return convertToDto(patient);
    }

    public void archiver(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        patientRepository.delete(patient);
    }

    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    private PatientDto convertToDto(Patient patient) {
        return PatientDto.builder()
                .id(patient.getId())
                .numeroPatient(patient.getNumeroPatient())
                .dateNaissance(patient.getDateNaissance())
                .sexe(patient.getSexe())
                .telephone(patient.getTelephone())
                .groupeSanguin(patient.getGroupeSanguin())
                .allergies(patient.getAllergies())
                .adresse(patient.getAdresse())
                .userId(patient.getUser() != null ? patient.getUser().getId() : null)
                .build();
    }

    private Patient convertToEntity(PatientDto dto) {
        return Patient.builder()
                .numeroPatient(dto.getNumeroPatient())
                .dateNaissance(dto.getDateNaissance())
                .sexe(dto.getSexe())
                .telephone(dto.getTelephone())
                .groupeSanguin(dto.getGroupeSanguin())
                .allergies(dto.getAllergies())
                .adresse(dto.getAdresse())
                .build();
    }
}
