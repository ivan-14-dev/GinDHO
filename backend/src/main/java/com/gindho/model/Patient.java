package com.gindho.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Superclass;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Superclass
public class Patient extends BaseEntity {
    @Column(name = "numero_patient", unique = true)
    private String numeroPatient;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    @Enumerated(EnumType.STRING)
    private Sexe sexe;

    private String telephone;
    private String groupeSanguin;
    
    @Column(length = 1000)
    private String allergies;
    
    @Column(length = 1000)
    private String adresse;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RendezVous> rendezVous = new ArrayList<>();

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DossierMedical> dossiersMedicaux = new ArrayList<>();

    public enum Sexe {
        MASCULIN, FEMININ, AUTRE
    }
}
