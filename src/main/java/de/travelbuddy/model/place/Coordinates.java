package de.travelbuddy.model.place;

import de.travelbuddy.model.place.exception.InvalidLatitudeException;
import de.travelbuddy.model.place.exception.InvalidLongitudeException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class which represents Coordinates
 */

@Entity
@Table(name = "COORDINATES")
@Getter
@Setter
public class Coordinates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;

    // Required for JPA
    public Coordinates() {};

    /**
     * coordinates has the exact position from a place
     * @param latitude is the width coordinate
     * @param longitude is the length coordinate
     * @throws InvalidLatitudeException the latitude isn't between -90 and 90
     * @throws InvalidLongitudeException the logitude isn't between -180 and 180
     */

    public Coordinates(double latitude, double longitude) throws InvalidLatitudeException, InvalidLongitudeException {
        if (!checkLatitude(latitude))
            throw new InvalidLatitudeException("Latitude should be between -90 and 90.");

        if (!checkLongitude(longitude))
            throw new InvalidLongitudeException("Longitude should be between -180 and 180.");

        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Check if the given latitude is valid
     * @param latitude The latitude to check
     * @return True, if the given value is valid, otherwise false
     */
    public static boolean checkLatitude(double latitude) {
        return latitude >= -90.0 && latitude <= 90.0;
    }

    /**
     * Check if the given longitude is valid
     * @param longitude The longitude to check
     * @return True, if the given value is valid, otherwise false
     */
    public static boolean checkLongitude(double longitude) {
        return longitude >= -180.0 && longitude <= 180.0;
    }


}
