package de.travelbuddy;
import static org.junit.jupiter.api.Assertions.*;
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

    // TODO Test schreiben in dem geprüft wird ob die Coordinaten im möglichen Bereich liegen


}



