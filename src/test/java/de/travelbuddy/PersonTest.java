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

        ContactDetails contact = new ContactDetails("9999999", "max@musterman.de", "Erfurt",
                "Altonaer Str.", 25, "99086", "Germany" );

        Person person = new Person(firstname, name, bday, contact);
        
        assertEquals(person.getFirstName(), firstname);
        assertEquals(person.getName(), name);
        assertEquals(person.getBirthdate(), bday);
    }
}