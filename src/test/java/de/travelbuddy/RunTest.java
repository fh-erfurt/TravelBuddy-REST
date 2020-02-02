package de.travelbuddy;

import de.travelbuddy.finance.exception.DuplicateExpenseException;
import de.travelbuddy.finance.Expense;
import de.travelbuddy.finance.exception.ExpenseNotFoundException;
import de.travelbuddy.journey.exception.DuplicateJourneyException;
import de.travelbuddy.journey.Journey;
import de.travelbuddy.journey.JourneyManager;
import de.travelbuddy.journey.exception.JourneyNotFoundException;
import de.travelbuddy.place.Connection;
import de.travelbuddy.place.exception.DuplicatePlaceException;
import de.travelbuddy.place.Place;
import de.travelbuddy.place.exception.PlaceNotFoundException;
import de.travelbuddy.utilities.InstanceHelper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RunTest {

    @Test
    public void complete_walkthrough_of_travelbuddy() throws JourneyNotFoundException {
        //Given
        JourneyManager manager = new JourneyManager();
        Journey journey = null;
        Place place = null;
        Person person = null;
        Expense expense1 = InstanceHelper.createExpense();
        Expense expense2 = InstanceHelper.createExpense();


        //When //Then
        runtest_assert_manager_journey(manager);
        journey = manager.getJourney(manager.getJourneyNames().get(0));
        runtest_assert_journey_place(journey);
        place = journey.getPlaces().get(0);
        runtest_assert_place_person(place);
        runtest_assert_place_expense(place);
        runtest_assert_place_connection(place);
    }


    private void runtest_assert_manager_journey(JourneyManager manager)
    {
        Journey journey1 = InstanceHelper.createJourney();
        Journey journey2 = InstanceHelper.createJourney();

        //Add journey, should not throw exception
        assertDoesNotThrow(() -> manager.addJourney(journey1));
        assertEquals(manager.journeyCount(), 1);

        //Add same journey, should throw exception
        assertThrows(DuplicateJourneyException.class, () -> manager.addJourney(journey1));
        assertEquals(manager.journeyCount(), 1);

        //Adding a new journey should not throw an exception
        assertDoesNotThrow(() -> manager.addJourney(journey2));
        assertEquals(manager.journeyCount(), 2);

        //Removing a journey that doesn't exist should throw an exception
        assertThrows(JourneyNotFoundException.class, () -> manager.removeJourney(InstanceHelper.createJourney()));
        assertEquals(manager.journeyCount(), 2);

        //Removing a journey that exists should not throw an exception
        assertDoesNotThrow(() -> manager.removeJourney(journey2));
        assertEquals(manager.journeyCount(), 1);
        assertDoesNotThrow(() -> manager.getJourney(journey1.getTitle()));
    }

    private void runtest_assert_journey_place(Journey journey1)
    {
        Place place1 = InstanceHelper.createPlace();
        Place place2 = InstanceHelper.createPlace();

        //Add place, should not throw exception
        assertDoesNotThrow(() -> journey1.addPlace(place1));
        assertEquals(journey1.getPlaces().size(), 1);

        //Add same place, should throw exception
        assertThrows(DuplicatePlaceException.class, () -> journey1.addPlace(place1));
        assertEquals(journey1.getPlaces().size(), 1);

        //Adding a new place should not throw an exception
        assertDoesNotThrow(() -> journey1.addPlace(place2));
        assertEquals(journey1.getPlaces().size(), 2);

        //Removing a place that doesn't exist should throw an exception
        assertThrows(PlaceNotFoundException.class, () -> journey1.removePlace(InstanceHelper.createPlace()));
        assertEquals(journey1.getPlaces().size(), 2);

        //Removing a journey that exists should not throw an exception
        assertDoesNotThrow(() -> journey1.removePlace(place2));
        assertEquals(journey1.getPlaces().size(), 1);
        assertDoesNotThrow(() -> journey1.findPlace(place1.getName()));
    }

    private void runtest_assert_place_person(Place place)
    {
        Person person1 = InstanceHelper.createPerson();
        Person person2 = InstanceHelper.createPerson();

        //Add person, should not throw exception
        assertDoesNotThrow(() -> place.addPerson(person1));
        assertEquals(place.getInvolvedPersons().size(), 1);

        //Add same person, should throw exception
        assertThrows(DuplicatePersonException.class, () -> place.addPerson(person1));
        assertEquals(place.getInvolvedPersons().size(), 1);

        //Adding a new person should not throw an exception
        assertDoesNotThrow(() -> place.addPerson(person2));
        assertEquals(place.getInvolvedPersons().size(), 2);

        //Removing a person that doesn't exist should throw an exception
        assertThrows(PersonNotFoundException.class, () -> place.removePerson(InstanceHelper.createPerson()));
        assertEquals(place.getInvolvedPersons().size(), 2);

        //Removing a person that exists should not throw an exception
        assertDoesNotThrow(() -> place.removePerson(person2));
        assertEquals(place.getInvolvedPersons().size(), 1);
        assertTrue(place.findPersons(person1.getName()).contains(person1));
    }

    private void runtest_assert_place_expense(Place place)
    {
        Expense expense1 = InstanceHelper.createExpense();
        Expense expense2 = InstanceHelper.createExpense();

        //Add expense, should not throw exception
        assertDoesNotThrow(() -> place.addExpense(expense1));
        assertEquals(place.getExpenses().size(), 1);

        //Add same expense, should throw exception
        assertThrows(DuplicateExpenseException.class, () -> place.addExpense(expense1));
        assertEquals(place.getExpenses().size(), 1);

        //Adding a new person should not throw an exception
        assertDoesNotThrow(() -> place.addExpense(expense2));
        assertEquals(place.getExpenses().size(), 2);

        //Removing a person that doesn't exist should throw an exception
        assertThrows(ExpenseNotFoundException.class, () -> place.removeExpense(InstanceHelper.createExpense()));
        assertEquals(place.getExpenses().size(), 2);

        //Removing a person that exists should not throw an exception
        assertDoesNotThrow(() -> place.removeExpense(expense1));
        assertEquals(place.getExpenses().size(), 1);
    }

    private void runtest_assert_place_connection(Place place)
    {
        Place place2 = InstanceHelper.createPlace();
        Connection con = InstanceHelper.createConnection(place, place2);
        Connection con2 = InstanceHelper.createConnection(place, place2);

        //Add connection, should not throw exception
        assertDoesNotThrow(() -> place.addConnection(con));
        assertEquals(place.getConnectionsToNextPlace().size(), 1);

        //Add same connection, should throw exception
        assertThrows(IllegalArgumentException.class, () -> place.addConnection(con));
        assertEquals(place.getConnectionsToNextPlace().size(), 1);

        //Adding a new connection should not throw an exception
        assertDoesNotThrow(() -> place.addConnection(con2));
        assertEquals(place.getConnectionsToNextPlace().size(), 2);

        //Removing a connection that doesn't exist should throw an exception
        assertThrows(IllegalArgumentException.class, () -> place.removeConnection(InstanceHelper.createConnection()));
        assertEquals(place.getConnectionsToNextPlace().size(), 2);

        //Removing a connection that exists should not throw an exception
        assertDoesNotThrow(() -> place.removeConnection(con2));
        assertEquals(place.getConnectionsToNextPlace().size(), 1);

        con.setUsed(true);
        assertTrue(con.getUsed());
    }
}
