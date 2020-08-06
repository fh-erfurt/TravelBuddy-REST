package de.travelbuddy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_base")
    private Long id;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime created;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime modified;

    @PrePersist
    void onCreate(){
        this.setCreated(LocalDateTime.now());
        this.setModified(this.getCreated());
    }

    @PreUpdate
    void onUpdate(){
        this.setModified(LocalDateTime.now());
    }
}
