package de.travelbuddy;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;


public class CoordinatesTest {
    @Test
    public void correctly_instantiate_Coordinates() {

        double latitude = 40.689249;
        double longitude = -74.044500;

        Coordinates coordinates = new Coordinates(latitude, longitude);

        assertEquals(coordinates.getLatitude(), latitude);
        assertEquals(coordinates.getLongitude(), longitude);
    }
/*
    @Test
    public void check_Coordinates_between_min_and_max() {
        //Given
        Coordinates check = new Coordinates(190.548203, -167.964066);

        //When
        boolean checkLa = check.checkLatitude(check.getLatitude());
        boolean checkLo = check.checkLongitude(check.getLongitude());


        //Then
        assertEquals(false, checkLa);
        assertEquals(true, checkLo);
    }
*/
}



