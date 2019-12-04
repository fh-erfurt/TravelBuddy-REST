package de.travelbuddy;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
public class AdressTest {
    @Test
    public void should_create_address (){

        String town = "Erfurt";
        String street = "Altonaer Straße";
        int streetNumber =  25;
        String ZIP = "99085";
        String country = "Deutschland";

        Adress adress = new Adress(town, street, streetNumber, ZIP, country);

        assertEquals(adress.getTown(), town);
        assertEquals(adress.getStreet(), street);
        assertEquals(adress.getStreetNumber(), streetNumber);
        assertEquals(adress.getZIP(), ZIP);
        assertEquals(adress.getCountry(), country);
    }


}
