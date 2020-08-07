package de.travelbuddy.model.place;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Sights is a extension from place
 */

@Entity

@Getter
@Setter
@NoArgsConstructor
public class Sight extends Place {

    private boolean indoor;
}
