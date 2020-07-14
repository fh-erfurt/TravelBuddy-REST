package de.travelbuddy;
import static org.junit.jupiter.api.Assertions.*;

import de.travelbuddy.model.ContactDetails;
import de.travelbuddy.model.Person;
import de.travelbuddy.utilities.InstanceHelper;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

public class PersonTest {
    @Test
    public void correctly_instantiate_person()
    {

        //Given
        String firstname = "Marcel";
        String name = "van der Heide";
        LocalDate bday = LocalDate.of(1990,5,2);
        ContactDetails contact = InstanceHelper.createContactDetails();

        //When
        Person person = new Person();
        person.setBirthdate(bday);
        person.setFirstName(firstname);
        person.setName(name);
        person.setContactDetails(contact);

        //Then
        assertEquals(person.getFirstName(), firstname);
        assertEquals(person.getFirstName(), "Marcel");
        assertEquals(person.getName(), name);
        assertEquals(person.getBirthdate(), bday);
        assertEquals(person.getContactDetails(), contact);
    }
}