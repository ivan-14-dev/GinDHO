package com.gindho.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @CreationTimestamp
    @Column(name = "cree_le", nullable = false, updatable = false)
    private LocalDateTime creeLe;

    @UpdateTimestamp
    @Column(name = "mis_a_jour_le", nullable = false)
    private LocalDateTime misAJourLe;

    @PrePersist
    protected void prePersist() {
        if (creeLe == null) {
            creeLe = LocalDateTime.now();
        }
        if (misAJourLe == null) {
            misAJourLe = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void preUpdate() {
        misAJourLe = LocalDateTime.now();
    }
}

