package de.travelbuddy.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter(AccessLevel.PRIVATE)
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_base")
    private Long id;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime created;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime modified;

    @PrePersist
    void onCreate(){
        this.setCreated(LocalDateTime.now());
    }

    @PreUpdate
    void onUpdate(){
        this.setModified(LocalDateTime.now());
    }
}
