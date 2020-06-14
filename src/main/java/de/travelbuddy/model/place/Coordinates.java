package de.travelbuddy.model.place;

import de.travelbuddy.model.place.exception.InvalidLatitudeException;
import de.travelbuddy.model.place.exception.InvalidLongitudeException;

import javax.persistence.*;

/**
 * Class which represents Coordinates
 */

@Entity
@Table(name = "COORDINATES")
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

    public double getLatitude() {return latitude;}

    public void setLatitude(double latitude) {this.latitude = latitude;}

    public double getLongitude() {return longitude;}

    public void setLongitude(double longitude) {this.longitude = longitude;}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

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