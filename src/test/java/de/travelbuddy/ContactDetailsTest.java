package de.travelbuddy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContactDetailsTest {

    @Test
    public void correctly_instantiate_ContactDetails(){

        String town = "Erfurt";
        String street = "Altonaer Straße";
        int streetNumber =  25;
        String ZIP = "99085";
        String country = "Deutschland";
        String phone = "036167000";
        String email = "rektorat@fh-erfurt.de";

        ContactDetails contact = new ContactDetails(phone, email, town,
                street, streetNumber, ZIP, country );

        assertEquals(contact.getTown(), town);
        assertEquals(contact.getStreet(), street);
        assertEquals(contact.getStreetNumber(), streetNumber);
        assertEquals(contact.getZIP(), ZIP);
        assertEquals(contact.getCountry(), country);
        assertEquals(contact.getPhone(), phone);
        assertEquals(contact.getEmail(), email);
    }
}
