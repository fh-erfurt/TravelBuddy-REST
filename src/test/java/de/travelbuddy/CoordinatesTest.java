package de.travelbuddy;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class CoordinatesTest {
    @Test
    public void correctly_instantiate_Coordinates() {

        String latitude = "40.689249";
        String longitude = "-74.044500";

        Coordinates coordinates = new Coordinates(latitude, longitude);

        assertEquals(coordinates.getLatitude(), latitude);
        assertEquals(coordinates.getLongitude(), longitude);
    }
}



