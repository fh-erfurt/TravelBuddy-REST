package de.travelbuddy.place;
import de.travelbuddy.*;
import de.travelbuddy.finance.Expense;
import de.travelbuddy.finance.Money;

import de.travelbuddy.utilities.InstanceHelper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaceTest {
    //Todo Test with multiple persons involved!
    @Test
    public void correctly_instantiate_place(){

        //Given
        String title = "Berlin";
        ContactDetails contact = InstanceHelper.createContactDetails();
        Coordinates coordinates = InstanceHelper.createCoordinate();
        LocalDateTime arrival = LocalDateTime.now();
        LocalDateTime departure = LocalDateTime.now().plusHours(5);
        Person person = InstanceHelper.createPersonMale();
        ArrayList<Person> persons = new ArrayList<Person>();
        Expense expense = InstanceHelper.createExpense();
        ArrayList<Expense> expenses = new ArrayList<Expense>();
        Connection connection = InstanceHelper.createConnection();
        ArrayList<Connection> connections = new ArrayList<Connection>();
        expenses.add(expense);
        persons.add(person);
        connections.add(connection);

        //When
        Place place = new Place(title, coordinates, contact, arrival, departure,
                expenses, connections, persons);

        //Then
        assertEquals(place.getName(), title);
        assertEquals(place.getArrive(), arrival);
        assertEquals(place.getDeparture(), departure);
        assertEquals(place.getContactDetails(), contact);
        assertEquals(place.getCoordinates(), coordinates);
        assertTrue(place.getInvolvedPersons().contains(person));
        assertEquals(place.getInvolvedPersons().size(), persons.size());
        assertTrue(place.getConnectionsToNextPlace().contains(connection));
        assertEquals(place.getConnectionsToNextPlace().size(), connections.size());
        assertTrue(place.getExpenses().contains(expense));
        assertEquals(place.getExpenses().size(), expenses.size());
    }

    @Test
    public void total_costs_correct_with_same_currency() {
        //Given
        Place place1 = InstanceHelper.createPlace(LocalDateTime.now());
        Expense expense1 = InstanceHelper.createExpense(Currency.getInstance("EUR"));
        Expense expense2 = InstanceHelper.createExpense(Currency.getInstance("EUR"));
        Expense expense3 = InstanceHelper.createExpense(Currency.getInstance("EUR"));
        Expense expense4 = InstanceHelper.createExpense(Currency.getInstance("EUR"));

        //When
        place1.addExpense(expense1);
        place1.addExpense(expense2);
        place1.addExpense(expense3);
        place1.addExpense(expense4);

        Money totalMoney = new Money(expense1.getPrice().getCurrency(), expense1.getPrice().getValue());
        totalMoney.add(expense2.getPrice());
        totalMoney.add(expense3.getPrice());
        totalMoney.add(expense4.getPrice());
        Money costs = place1.totalCost(Currency.getInstance("EUR"));

        //Then
        assertEquals(totalMoney.getValue(), costs.getValue());
    }

    @Test
    public void total_costs_person_correct_with_different_currency() {
        //Given
        Place place1 = InstanceHelper.createPlace(LocalDateTime.now());
        Expense expense1 = InstanceHelper.createExpense(Currency.getInstance("EUR"));
        Expense expense2 = InstanceHelper.createExpense(Currency.getInstance("USD"));
        Expense expense3 = InstanceHelper.createExpense(Currency.getInstance("EUR"));
        Expense expense4 = InstanceHelper.createExpense(Currency.getInstance("USD"));

        //When
        place1.addExpense(expense1);
        place1.addExpense(expense2);
        place1.addExpense(expense3);
        place1.addExpense(expense4);

        Money totalMoney = new Money(expense1.getPrice().getCurrency(), expense1.getPrice().getValue());
        totalMoney.add(expense2.getPrice());
        totalMoney.add(expense3.getPrice());
        totalMoney.add(expense4.getPrice());
        Money costs = place1.totalCost(Currency.getInstance("EUR"));

        //Then
        assertEquals(totalMoney.getValue(), costs.getValue());
    }
}