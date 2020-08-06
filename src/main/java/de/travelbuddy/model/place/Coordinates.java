package de.travelbuddy.model.place;

import de.travelbuddy.model.BaseModel;
import de.travelbuddy.model.place.exception.InvalidLatitudeException;
import de.travelbuddy.model.place.exception.InvalidLongitudeException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Class which represents Coordinates
 */

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class Coordinates extends BaseModel {

    @Column(nullable = false)
    private double latitude;
    @Column(nullable = false)
    private double longitude;


    public double getLatitude() {return latitude;}

    public void setLatitude(double latitude) throws InvalidLatitudeException {
        if (!checkLatitude(latitude))
            throw new InvalidLatitudeException("Latitude should be between -90 and 90.");
        this.latitude = latitude;
    }

    public double getLongitude() {return longitude;}

    public void setLongitude(double longitude) throws InvalidLongitudeException {
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
