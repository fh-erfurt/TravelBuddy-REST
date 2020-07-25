package de.travelbuddy.model.place;

import de.travelbuddy.model.DuplicatePersonException;
import de.travelbuddy.model.PersonNotFoundException;
import de.travelbuddy.model.finance.exception.DuplicateExpenseException;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.finance.Money;
import de.travelbuddy.model.ContactDetails;
import de.travelbuddy.model.Person;

import de.travelbuddy.model.finance.exception.ExpenseNotFoundException;
import de.travelbuddy.model.finance.exception.NotSupportedCurrencyException;
import de.travelbuddy.utilities.InstanceHelper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class PlaceTest {

    @Test
    public void correctly_instantiate_place(){

        //Given
        String title = "Berlin";
        ContactDetails contact = InstanceHelper.createContactDetails();
        Coordinates coordinates = InstanceHelper.createCoordinate();
        LocalDateTime arrival = LocalDateTime.now();
        LocalDateTime departure = LocalDateTime.now().plusHours(5);
        Person person = InstanceHelper.createPersonMale();
        ArrayList<Person> persons = new ArrayList<>();
        Expense expense = InstanceHelper.createExpense();
        Map<Long, Expense> expenses = new HashMap<>();
        Connection connection = InstanceHelper.createConnection();
        ArrayList<Connection> connections = new ArrayList<>();
        expenses.put(expense.getId(), expense);
        persons.add(person);
        connections.add(connection);

        //When
        Place place = new Place();
        place.setName(title);
        place.setCoordinates(coordinates);
        place.setContactDetails(contact);
        place.setArrive(arrival);
        place.setDeparture(departure);
        place.setExpenses(expenses);
        place.setConnectionsToNextPlace(connections);
        place.setInvolvedPersons(persons);

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
        assertTrue(place.getExpenses().containsKey(expense.getTitle()));
        assertEquals(place.getExpenses().size(), expenses.size());
    }

    @Test
    public void total_costs_correct_with_same_currency() throws DuplicateExpenseException {
        //Given
        Place place1 = InstanceHelper.createPlace(LocalDateTime.now());
        Currency currency = Currency.getInstance("EUR");
        Expense expense1 = InstanceHelper.createExpense(currency);
        Expense expense2 = InstanceHelper.createExpense(currency);
        Expense expense3 = InstanceHelper.createExpense(currency);
        Expense expense4 = InstanceHelper.createExpense(currency);

        Money money1 = new Money(); money1.setCurrency(currency); money1.setValue(BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP));
        Money money2 = new Money(); money2.setCurrency(currency); money2.setValue(BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_UP));
        Money money3 = new Money(); money3.setCurrency(currency); money3.setValue(BigDecimal.valueOf(30).setScale(2, RoundingMode.HALF_UP));
        Money money4 = new Money(); money4.setCurrency(currency); money4.setValue(BigDecimal.valueOf(40).setScale(2, RoundingMode.HALF_UP));

        expense1.setPrice(money1);
        expense2.setPrice(money2);
        expense3.setPrice(money3);
        expense4.setPrice(money4);

        //When
        place1.addExpense(expense1);
        place1.addExpense(expense2);
        place1.addExpense(expense3);
        place1.addExpense(expense4);

        Money totalMoney = new Money();
        totalMoney.setCurrency(Currency.getInstance("EUR"));
        totalMoney.setValue(BigDecimal.valueOf(0).setScale(2,RoundingMode.HALF_UP));
        totalMoney.add(expense1.getPrice());
        totalMoney.add(expense2.getPrice());
        totalMoney.add(expense3.getPrice());
        totalMoney.add(expense4.getPrice());
        Money costs = place1.totalCost(Currency.getInstance("EUR"));

        //Then
        assertEquals(totalMoney.getValue(), costs.getValue());
    }

    @Test
    public void total_costs_correct_with_different_currencies() throws DuplicateExpenseException {
        //Given
        Currency targetCurrency = Currency.getInstance("EUR");
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

        Money totalMoney = new Money();
        totalMoney.setCurrency(targetCurrency);
        totalMoney.setValue(BigDecimal.valueOf(0).setScale(2,RoundingMode.HALF_UP));

        totalMoney.add(expense1.getPrice());
        totalMoney.add(expense2.getPrice());
        totalMoney.add(expense3.getPrice());
        totalMoney.add(expense4.getPrice());
        Money costs = place1.totalCost(targetCurrency);

        //Then
        assertEquals(totalMoney.getValue(), costs.getValue());
    }

    @Test
    public void total_cost_per_person_with_same_currencies () throws NotSupportedCurrencyException, DuplicateExpenseException {
        //Given
        Place place1 = InstanceHelper.createPlace(LocalDateTime.now());
        Currency targetCurrency = Currency.getInstance("EUR");

        Expense expense1 = InstanceHelper.createExpense(Currency.getInstance("EUR"));
        Expense expense2 = InstanceHelper.createExpense(Currency.getInstance("EUR"));
        Expense expense3 = InstanceHelper.createExpense(Currency.getInstance("EUR"));
        Expense expense4 = InstanceHelper.createExpense(Currency.getInstance("EUR"));

        Person person1 = InstanceHelper.createPerson();
        Person person2 = InstanceHelper.createPerson();
        Person person3 = InstanceHelper.createPerson();
        Person person4 = InstanceHelper.createPerson();

        expense1.addPerson(person1);
        expense1.addPerson(person2);
        expense2.addPerson(person1);
        expense2.addPerson(person3);
        expense4.addPerson(person2);

        place1.addExpense(expense1);
        place1.addExpense(expense2);
        place1.addExpense(expense3);
        place1.addExpense(expense4);

        //When
        Money expectedMoney = expense1.getPrice().convert(targetCurrency);
        expectedMoney.add(expense4.getPrice()).convert(targetCurrency);

        Money calculatedMoney = place1.totalCostOfPerson(targetCurrency,person2);

        //Then
        assertEquals(expectedMoney.getValue(), calculatedMoney.getValue());
        assertEquals(targetCurrency,calculatedMoney.getCurrency());
    }

    @Test
    public void total_cost_per_person_with_different_currencies () throws NotSupportedCurrencyException, DuplicateExpenseException {
        //Given
        Place place1 = InstanceHelper.createPlace(LocalDateTime.now());
        Currency targetCurrency = Currency.getInstance("EUR");

        Expense expense1 = InstanceHelper.createExpense(Currency.getInstance("RUB"));
        Expense expense2 = InstanceHelper.createExpense(Currency.getInstance("USD"));
        Expense expense3 = InstanceHelper.createExpense(Currency.getInstance("EUR"));
        Expense expense4 = InstanceHelper.createExpense(Currency.getInstance("USD"));

        Person person1 = InstanceHelper.createPerson();
        Person person2 = InstanceHelper.createPerson();
        Person person3 = InstanceHelper.createPerson();
        Person person4 = InstanceHelper.createPerson();

        expense1.addPerson(person1);
        expense1.addPerson(person2);
        expense2.addPerson(person1);
        expense2.addPerson(person3);
        expense2.addPerson(person4);
        expense4.addPerson(person2);

        place1.addExpense(expense1);
        place1.addExpense(expense2);
        place1.addExpense(expense3);
        place1.addExpense(expense4);

        //When
        Money expectedMoney = expense1.getPrice().convert(targetCurrency);
        expectedMoney.add(expense4.getPrice()).convert(targetCurrency);

        Money calculatedMoney = place1.totalCostOfPerson(targetCurrency,person2);

        //Then
        assertEquals(expectedMoney.getValue(), calculatedMoney.getValue());
        assertEquals(targetCurrency,calculatedMoney.getCurrency());
    }

    @Test
    public void place_should_add_new_person() throws DuplicatePersonException {
        //Given
        Place place = InstanceHelper.createPlace();
        Person person = InstanceHelper.createPerson();

        //When
        place.addPerson(person);

        //Then
        assertEquals(place.getInvolvedPersons().size(), 1);
        assertEquals(place.getInvolvedPersons().get(0), person);
    }

    @Test
    public void place_should_remove_person() throws DuplicatePersonException, PersonNotFoundException {
        //Given
        Place place = InstanceHelper.createPlace();
        Person person = InstanceHelper.createPerson();

        //When
        place.addPerson(person);
        place.removePerson(person);

        //Then
        assertEquals(place.getInvolvedPersons().size(), 0);
    }

    @Test
    public void place_should_add_expense() throws DuplicateExpenseException {
        //Given
        Place place = InstanceHelper.createPlace();
        Expense expense = InstanceHelper.createExpense();

        //When
        place.addExpense(expense);

        //Then
        assertEquals(place.getExpenses().size(), 1);
        assertEquals(place.getExpenses().get(expense.getTitle()), expense);
    }

    @Test
    public void place_should_remove_expense() throws DuplicateExpenseException, ExpenseNotFoundException {
        //Given
        Place place = InstanceHelper.createPlace();
        Expense expense = InstanceHelper.createExpense();

        //When
        place.addExpense(expense);
        place.removeExpense(expense);

        //Then
        assertEquals(place.getExpenses().size(), 0);
    }

    @Test
    public void place_should_throw_duplicate_person_exception() throws DuplicatePersonException {
        //Given
        Place place = InstanceHelper.createPlace();
        Person person = InstanceHelper.createPerson();

        //When
        place.addPerson(person);
        Exception exception = assertThrows(DuplicatePersonException.class, () -> place.addPerson(person));

        //Then
        assertTrue(exception.getMessage().contains(String.format("Person '%s' is already involved.", person.getName())));
    }

    @Test
    public void place_should_throw_person_not_found_exception() {
        //Given
        Place place = InstanceHelper.createPlace();
        Person person = InstanceHelper.createPerson();

        //When
        Exception exception = assertThrows(PersonNotFoundException.class,
                () -> place.removePerson(person));

        //Then
        assertTrue(exception.getMessage().contains(String.format("Person '%s' is not involved.", person.getName())));
    }

    @Test
    public void place_should_throw_expense_not_found_exception() {
        //Given
        Place place = InstanceHelper.createPlace();
        Expense expense = InstanceHelper.createExpense();

        //When
        Exception exception = assertThrows(ExpenseNotFoundException.class,
                () -> place.removeExpense(expense));

        //Then
        assertTrue(exception.getMessage().contains(String.format("Expense '%s' does not exist.", expense.getTitle())));
    }

    @Test
    public void place_should_throw_duplicate_expense_exception() throws DuplicateExpenseException {
        //Given
        Place place = InstanceHelper.createPlace();
        Expense expense = InstanceHelper.createExpense();

        //When
        place.addExpense(expense);
        Exception exception = assertThrows(DuplicateExpenseException.class,
                () -> place.addExpense(expense));

        //Then
        assertTrue(exception.getMessage().contains(String.format("Expense '%s' does already exist.", expense.getTitle())));
    }
}