package com.learn.quizapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "base_seq_gen")
    protected Long id;

    @Column(nullable = false, updatable = false)
    protected UUID uuid;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    protected Instant createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    protected Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
    }
}
