package de.travelbuddy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import java.time.LocalDate;

public class PersonTest {
    @Test
    public void correctly_instantiate_person()
    {

        String firstname = "Marcel";
        String name = "van der Heide";
        LocalDate bday = LocalDate.of(1990,5,2);
        String town = "Erfurt",
        String street = "Altonaer Straße",
        int streetNumber =  25,
        String ZIP = "99085",
        String country = "Deutschland",
        String phone = "036167000",
        String email = "rektorat@fh-erfurt.de");
        ContactDetails contact = new ContactDetails(phone, email, town,street, streetNumber,ZIP,country );

        Person person = new Person(firstname, name, bday, contact);
        
        assertEquals(person.getFirstName(), firstname);
        assertEquals(person.getName(), name);
        assertEquals(person.getBirthdate(), bday);
    }
}