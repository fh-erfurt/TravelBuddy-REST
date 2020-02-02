package de.travelbuddy.place;

import de.travelbuddy.DuplicatePersonException;
import de.travelbuddy.PersonNotFoundException;
import de.travelbuddy.finance.exception.DuplicateExpenseException;
import de.travelbuddy.finance.Expense;
import de.travelbuddy.finance.Money;
import de.travelbuddy.ContactDetails;
import de.travelbuddy.Person;

import de.travelbuddy.finance.exception.ExpenseNotFoundException;
import de.travelbuddy.finance.exception.NotSupportedCurrencyException;
import de.travelbuddy.utilities.InstanceHelper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

public class PlaceTest {

    // TODO noch einige mögliche Tests zu schreiben (add remove etc.)
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
        ArrayList<Expense> expenses = new ArrayList<>();
        Connection connection = InstanceHelper.createConnection();
        ArrayList<Connection> connections = new ArrayList<>();
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
        assertTrue(place.getListOfInvolvedPersons().contains(person));
        assertEquals(place.getListOfInvolvedPersons().size(), persons.size());
        assertTrue(place.getConnectionsToNextPlace().contains(connection));
        assertEquals(place.getConnectionsToNextPlace().size(), connections.size());
        assertTrue(place.getExpenses().contains(expense));
        assertEquals(place.getExpenses().size(), expenses.size());
    }

    @Test
    public void total_costs_correct_with_same_currency() throws DuplicateExpenseException {
        //Given
        Place place1 = InstanceHelper.createPlace(LocalDateTime.now());
        Expense expense1 = InstanceHelper.createExpense(Currency.getInstance("EUR"));
        Expense expense2 = InstanceHelper.createExpense(Currency.getInstance("EUR"));
        Expense expense3 = InstanceHelper.createExpense(Currency.getInstance("EUR"));
        Expense expense4 = InstanceHelper.createExpense(Currency.getInstance("EUR"));

        expense1.setPrice(new Money(Currency.getInstance("EUR"), BigDecimal.valueOf(10).setScale(2, RoundingMode.HALF_UP)));
        expense2.setPrice(new Money(Currency.getInstance("EUR"), BigDecimal.valueOf(20).setScale(2, RoundingMode.HALF_UP)));
        expense3.setPrice(new Money(Currency.getInstance("EUR"), BigDecimal.valueOf(30).setScale(2, RoundingMode.HALF_UP)));
        expense4.setPrice(new Money(Currency.getInstance("EUR"), BigDecimal.valueOf(40).setScale(2, RoundingMode.HALF_UP)));

        //When
        place1.addExpense(expense1);
        place1.addExpense(expense2);
        place1.addExpense(expense3);
        place1.addExpense(expense4);

        Money totalMoney = new Money(Currency.getInstance("EUR"),BigDecimal.valueOf(0).setScale(2,RoundingMode.HALF_UP));
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

        Money totalMoney = new Money(targetCurrency,BigDecimal.valueOf(0).setScale(2,RoundingMode.HALF_UP));

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
        assertEquals(place.getListOfInvolvedPersons().size(), 1);
        assertEquals(place.getListOfInvolvedPersons().get(0), person);
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
        assertEquals(place.getListOfInvolvedPersons().size(), 0);
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
        assertEquals(place.getExpenses().get(0), expense);
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
    public void place_should_throw_person_not_found_exception() throws PersonNotFoundException {
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
    public void place_should_throw_expense_not_found_exception() throws ExpenseNotFoundException {
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