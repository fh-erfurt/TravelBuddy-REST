package de.travelbuddy.model.place;

        import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Accommodation is a extension from place
 */
@Entity
@Table(name = "ACCOMMODATION")
@Getter @Setter
public class Accommodation extends Place {

    @Enumerated()
    @Column(columnDefinition = "smallint")
    private accommodationType type;

    public enum accommodationType
    {
        HOTEL,HOSTEL,CAMPING,COUCHSURF,AIRBNB
    }

    // Required for JPA
    public Accommodation() {};

    public accommodationType getType() {return type;}

    public void setType(accommodationType type) {this.type = type;}

}
