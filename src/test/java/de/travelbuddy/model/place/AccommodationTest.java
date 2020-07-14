package de.travelbuddy.model.place;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import de.travelbuddy.model.ContactDetails;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.place.exception.InvalidLatitudeException;
import de.travelbuddy.model.place.exception.InvalidLongitudeException;
import de.travelbuddy.utilities.InstanceHelper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

public class AccommodationTest {
    @Test
    public void correctly_instantiate_Accommodation() throws InvalidLatitudeException, InvalidLongitudeException {
        String name = "Lee Valley Camping and Caravan Park";
        Coordinates coordinates = InstanceHelper.createCoordinate();
        ContactDetails contactDetails = InstanceHelper.createContactDetails();
        LocalDateTime arrive = LocalDateTime.of(2020,7,25,16,45);
        LocalDateTime departure = LocalDateTime.of(2020,7,28,20,5);
        Expense expense = InstanceHelper.createExpense();
        Map<String, Expense> expenses = Map.of(expense.getTitle(), expense);
        List<Connection> connectionsToNextPlace = new ArrayList<>();
        List<Person> involvedPersons = new ArrayList<>(Collections.singletonList(InstanceHelper.createPerson()));
        Accommodation.accommodationType type = Accommodation.accommodationType.CAMPING;

        Accommodation accommodation = new Accommodation();
        accommodation.setName(name);
        accommodation.setType(type);
        accommodation.setCoordinates(coordinates);
        accommodation.setArrive(arrive);
        accommodation.setDeparture(departure);
        accommodation.setExpenses(expenses);
        accommodation.setConnectionsToNextPlace(connectionsToNextPlace);
        accommodation.setInvolvedPersons(involvedPersons);
        accommodation.setContactDetails(contactDetails);

        assertEquals(accommodation.getName(), name);
        assertEquals("Lee Valley Camping and Caravan Park", name);
        assertNotEquals("Lee Valley Park", name);
        assertEquals(accommodation.getCoordinates(), coordinates);
        assertEquals(accommodation.getContactDetails(), contactDetails);
        assertEquals(accommodation.getArrive(), arrive);
        assertEquals(accommodation.getDeparture(), departure);
        assertEquals(accommodation.getExpenses(), expenses);
        assertEquals(accommodation.getConnectionsToNextPlace(), connectionsToNextPlace);
        assertEquals(accommodation.getInvolvedPersons(), involvedPersons);
        assertEquals(accommodation.getType(), type);
    }
}




