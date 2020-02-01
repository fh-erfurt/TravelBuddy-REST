package de.travelbuddy;
import static org.junit.jupiter.api.Assertions.*;

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
        String town = "Erfurt";
        String street = "Altonaer Straße";
        int streetNumber =  25;
        String ZIP = "99085";
        String country = "Deutschland";
        String phone = "036167000";
        String email = "rektorat@fh-erfurt.de";
        ContactDetails contact = new ContactDetails(phone, email, town,street, streetNumber,ZIP,country );

        //When
        Person person = new Person(firstname, name, bday, contact);

        //Then
        assertEquals(person.getFirstName(), firstname);
        assertEquals(person.getFirstName(), "Marcel");
        assertEquals(person.getName(), name);
        assertEquals(person.getBirthdate(), bday);
        assertEquals(person.getContactDetails(), contact);
    }
    @Test
    public void false_date_person(){
        //Given
        String firstname = "Frieder";
        String name = "Ullmann";
        LocalDate bday = LocalDate.of(1995,7,8);
        String town = "Erfurt";
        String street = "Friedrich-Engels Str.";
        int streetNumber =  13;
        String ZIP = "99086";
        String country = "Deutschland";
        String phone = "036167000";
        String email = "rektorat@fh-erfurt.de";
        ContactDetails contact = new ContactDetails(phone, email, town,street, streetNumber,ZIP,country );

        //When
        Person person = new Person(firstname, name, bday, contact);
        Person person2 = new Person("Marcel",name, bday, contact);

        //Then
        assertNotEquals(person2.getFirstName(), person.getFirstName());
        assertNotEquals(person2.getFirstName(), name);
        assertEquals(person.getFirstName(), firstname);
    }
}