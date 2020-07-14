package de.travelbuddy.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@Getter
public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Setter(AccessLevel.PRIVATE)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Setter(AccessLevel.PRIVATE)
    private Date modified;

    @PrePersist
    void onCreate(){
        this.setCreated(new Date());
    }

    @PreUpdate
    void onUpdate(){
        this.setModified(new Date());
    }
}
