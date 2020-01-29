package de.travelbuddy;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import de.travelbuddy.*;
import de.travelbuddy.place.Place;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JourneyTest {
    @Test
    public void correctly_instantiate_journey(){

        //Given
        string title    = "Berlin";

        ContactDetails contact = new ContactDetails("030115", "brandenburger@tor.berlin", "Berlin",
                "Pariser Platz", 1, "10117", "Germany" );

        LocalDate bday = LocalDate.of(1995,7,8);
        ContactDetails contact2 = new ContactDetails("015777820000", "frieder.ullmann@fh-erfurt.de", "Erfurt",
                "Friedrich-Engels Str.", 13, "99086", "Germany" );

        Person person = new Person("Frieder", "Ullmann", bday, contact2);

        Place place = new Place("Brandenburgertor", new Coordinates("52.516275", "13.377704"), contact,
                LocalDateTime.now(), LocalDateTime.now().plusHours(5), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(ArrayList.List(person)), false);



        //When
        Journey journey = new Journey(title, new ArrayList<>(ArrayList.asList(place)), new ArrayList<>(ArrayList.asList(person)));



        //Then
        assertEquals(journey.getTitle(), title);
        //assertEquals(journey.addPlace(), place);


    }

}

