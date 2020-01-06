package de.travelbuddy;

import static org.junit.jupiter.api.Assertions.*;

import de.travelbuddy.place.Place;
import org.junit.jupiter.api.Test;
import de.travelbuddy.Journey;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JourneyTest {

    @Test
    public void correctly_instantiate_journey(){

        String title = "Trip";
        List<Place> places = new ArrayList<>();
        List<Person> persons = new ArrayList<>();

        Journey journey = new Journey(title, places, persons);

        assertEquals(journey.getTitle(), title);
        assertEquals(journey.getPlaces(), places);
        assertEquals(journey.getPersons(), persons);
    }
}

