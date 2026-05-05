package com.gindho.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Superclass;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "medecins")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Superclass
public class Medecin extends BaseEntity {
    @Column(name = "numero_ordre", unique = true, nullable = false)
    private String numeroOrdre;

    private String specialisation;
    private String telephoneCabinet;

    @Column(nullable = false)
    private boolean disponible = true;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "medecin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RendezVous> rendezVous = new ArrayList<>();

    @OneToMany(mappedBy = "medecin", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Disponibilite> disponibilites = new ArrayList<>();
}
