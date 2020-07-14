package de.travelbuddy.model.place;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Sights is a extension from place
 */

@Entity
@Table(name = "SIGHT")
@Getter
@Setter
public class Sight extends Place {

    private boolean indoor;

    // Required for JPA
    public Sight() {};
}
