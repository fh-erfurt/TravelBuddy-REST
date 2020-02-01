package de.travelbuddy;
import static org.junit.jupiter.api.Assertions.*;

import de.travelbuddy.place.Coordinates;
import org.junit.jupiter.api.Test;


public class CoordinatesTest {
    @Test
    public void correctly_instantiate_Coordinates() {

        double latitude = 40.689249;
        double longitude = -74.044500;

        Coordinates coordinates = new Coordinates(latitude, longitude);

        assertEquals(coordinates.getLatitude(), latitude);
        assertEquals(coordinates.getLongitude(), longitude);
    }

    /* TODO Build tests with exceptions
    @Test
    public void instantiate_Coordinates_with_invalid_latitude_should_throw_exception() {
        //Given
        double latitude = 92.689249;
        double longitude = -74.044500;

        //When
        Coordinates coordinates = new Coordinates(latitude, longitude);

        //Then

    }

    @Test
    public void instantiate_Coordinates_with_invalid_longitude_should_throw_exception() {
        //Given
        double latitude = 40.689249;
        double longitude = -190.044500;

        //When
        Coordinates coordinates = new Coordinates(latitude, longitude);

        //Then

    }*/

    @Test
    public void check_latitude_and_longitude() {
        //Given
        double testLatitude = 40.689249;
        double testLongitude = -178.044500;

        //When
        boolean checkLa = Coordinates.checkLatitude(testLatitude);
        boolean checkLo = Coordinates.checkLongitude(testLongitude);

        //Then
        assertTrue(checkLa);
        assertTrue(checkLo);
    }

}



