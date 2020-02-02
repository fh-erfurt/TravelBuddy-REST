package de.travelbuddy.place;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.travelbuddy.*;
import de.travelbuddy.finance.Expense;
import de.travelbuddy.place.exception.InvalidLatitudeException;
import de.travelbuddy.place.exception.InvalidLongitudeException;
import de.travelbuddy.utilities.InstanceHelper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccommodationTest {
    @Test
    public void correctly_instantiate_Accommodation() throws InvalidLatitudeException, InvalidLongitudeException {
        String name = "Lee Valley Camping and Caravan Park";
        Coordinates coordinates = new Coordinates(-0.006111, 51.654722);
        ContactDetails contactDetails = new ContactDetails("+ 20 / 85 29 56 89", "scs@leevalleypark.org.uk",
                "London", "Sewardstone Road ", 1, "E4 7RA", "England");
        LocalDateTime arrive = LocalDateTime.of(2020,7,25,16,45);
        LocalDateTime departure = LocalDateTime.of(2020,7,28,20,5);
        List<Expense> expenses = new ArrayList<>(Arrays.asList(InstanceHelper.createExpense()));
        List<Connection> connectionsToNextPlace = new ArrayList<>();
        List<Person> involvedPersons = new ArrayList<>(Arrays.asList(InstanceHelper.createPerson()));
        Accommodation.accommodationType type = Accommodation.accommodationType.CAMPING;

        Accommodation accommodation = new Accommodation (name, coordinates, contactDetails, arrive, departure,
                expenses, connectionsToNextPlace, involvedPersons, type);

        assertEquals(accommodation.getName(), name);
        assertEquals(accommodation.getCoordinates(), coordinates);
        assertEquals(accommodation.getContactDetails(), contactDetails);
        assertEquals(accommodation.getArrive(), arrive);
        assertEquals(accommodation.getDeparture(), departure);
        assertEquals(accommodation.getExpenses(), expenses);
        assertEquals(accommodation.getConnectionsToNextPlace(), connectionsToNextPlace);
        assertEquals(accommodation.getListOfInvolvedPersons(), involvedPersons);
        assertEquals(accommodation.getType(), type);
    }
}




