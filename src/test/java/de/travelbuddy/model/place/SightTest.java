package de.travelbuddy.model.place;

import de.travelbuddy.model.ContactDetails;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.place.exception.InvalidLatitudeException;
import de.travelbuddy.model.place.exception.InvalidLongitudeException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SightTest {
    @Test
    public void correctly_instantiate_Sight_Louvre() throws InvalidLatitudeException, InvalidLongitudeException {

        String name = "Louvre";
        Coordinates coordinates = new Coordinates(48.864824, 2.334595);
        ContactDetails contactDetails = new ContactDetails("+33 1 40 20 50 50", "louvre@paris.fr",
                "Paris", "Rue de Rivoli", 1, "75001", "Frankreich");
        LocalDateTime arrive = LocalDateTime.of(2020,7,25,16,45);
        LocalDateTime departure = LocalDateTime.of(2020,7,28,20,5);
        Map<String, Expense> expenses = new HashMap<>();
        List<Connection> connectionsToNextPlace = new ArrayList<>();
        List<Person> involvedPersons = new ArrayList<>();
        boolean indoor = true;

        Sight sight = new Sight(name, coordinates, contactDetails, arrive, departure, expenses, connectionsToNextPlace,
                involvedPersons, true);

        assertEquals(sight.getName(), name);
        assertEquals(sight.getCoordinates(), coordinates);
        assertEquals(sight.getContactDetails(), contactDetails);
        assertEquals(sight.getArrive(), arrive);
        assertEquals(sight.getDeparture(), departure);
        assertEquals(sight.getExpenses(), expenses);
        assertEquals(sight.getConnectionsToNextPlace(), connectionsToNextPlace);
        assertEquals(sight.getInvolvedPersons(), involvedPersons);
        assertTrue(sight.isIndoor());
    }
}

