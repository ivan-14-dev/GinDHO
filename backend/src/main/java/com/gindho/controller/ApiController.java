package com.gindho.controller;

import com.gindho.dto.AuthenticationRequest;
import com.gindho.dto.AuthenticationResponse;
import com.gindho.dto.PatientDto;
import com.gindho.dto.RendezVousDto;
import com.gindho.service.PatientService;
import com.gindho.service.RendezVousService;
import com.gindho.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final UserService userService;
    private final PatientService patientService;
    private final RendezVousService rendezVousService;

    @PostMapping("/auth/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(userService.authenticate(request));
    }

    @GetMapping("/patients")
    public ResponseEntity<Page<PatientDto>> searchPatients(
            @RequestParam(required = false) String nom,
            Pageable pageable) {
        return ResponseEntity.ok(patientService.rechercher(nom, pageable));
    }

    @PostMapping("/patients")
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto dto) {
        return ResponseEntity.ok(patientService.creer(dto));
    }

    @PutMapping("/patients/{id}")
    public ResponseEntity<PatientDto> updatePatient(
            @PathVariable Long id, @RequestBody PatientDto dto) {
        return ResponseEntity.ok(patientService.mettreAJour(id, dto));
    }

    @GetMapping("/patients/{id}/dossier")
    public ResponseEntity<PatientDto> getDossier(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getDossier(id));
    }

    @PostMapping("/rendezvous")
    public ResponseEntity<RendezVousDto> createRendezVous(@RequestBody RendezVousDto dto) {
        return ResponseEntity.ok(rendezVousService.prendreRDV(dto));
    }

    @DeleteMapping("/rendezvous/{id}")
    public ResponseEntity<Void> cancelRendezVous(
            @PathVariable Long id, @RequestParam String motif) {
        rendezVousService.annuler(id, motif);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rendezvous/patient/{patientId}")
    public ResponseEntity<List<RendezVousDto>> getRendezVousByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(rendezVousService.getRDVsPatient(patientId));
    }

    @GetMapping("/rendezvous/medecin/{medecinId}")
    public ResponseEntity<List<RendezVousDto>> getRendezVousByMedecin(
            @PathVariable Long medecinId, @RequestParam String date) {
        return ResponseEntity.ok(rendezVousService.getRDVsMedecin(medecinId, 
                java.time.LocalDateTime.parse(date)));
    }
}
