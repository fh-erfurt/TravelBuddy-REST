package de.travelbuddy;

import static org.junit.jupiter.api.Assertions.*;

import de.travelbuddy.journey.Journey;
import de.travelbuddy.place.Sight;
import org.junit.jupiter.api.Test;
import de.travelbuddy.Person;
import de.travelbuddy.place.Place;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;



public class JourneyTest {

    @Test
    public void correctly_instantiate_journey(){

        //Given
        String title = "Berlin";

        ContactDetails contact = new ContactDetails("030115", "brandenburger@tor.berlin", "Berlin",
                "Pariser Platz", 1, "10117", "Germany" );

        LocalDate bday = LocalDate.of(1995,7,8);
        ContactDetails contact2 = new ContactDetails("015777820000", "frieder.ullmann@fh-erfurt.de", "Erfurt",
                "Friedrich-Engels Str.", 13, "99086", "Germany" );


        Place place = new Sight("Brandenburgertor", new Coordinates("52.516275", "13.377704"), contact,
                LocalDateTime.now(), LocalDateTime.now().plusHours(5), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), false);

        Person person = new Person("Frieder", "Ullmann", bday, contact2);


        //When
        Journey journey = new Journey(title, new ArrayList<>(), new ArrayList<>());



        //Then
        assertEquals(journey.getTitle(), title);
        //assertEquals(journey.addPlace(), place);


    }

}

