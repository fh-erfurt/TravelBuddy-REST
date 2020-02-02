package de.travelbuddy.journey;

import de.travelbuddy.ContactDetails;
import de.travelbuddy.Person;
import de.travelbuddy.finance.DuplicateExpenseException;
import de.travelbuddy.finance.Expense;
import de.travelbuddy.finance.Money;
import de.travelbuddy.place.DuplicatePlaceException;
import de.travelbuddy.place.Place;
import de.travelbuddy.place.PlaceNotFoundException;
import de.travelbuddy.utilities.InstanceHelper;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JourneyTest {
    @Test
    public void correctly_instantiate_journey(){

        //Given
        String title = "Berlin";
        ContactDetails contact = InstanceHelper.createContactDetails();
        ContactDetails contact2 = InstanceHelper.createContactDetails();
        Place place = InstanceHelper.createPlace(LocalDateTime.now());
        ArrayList<Place> places = new ArrayList<>();
        places.add(place);
        Person person = InstanceHelper.createPersonMale();
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(person);

        //When
        Journey journey = new Journey(title, places, persons);


        //Then
        assertEquals(journey.getTitle(), title);
        assertTrue(journey.getPlaces().contains(place));
        assertTrue(journey.getPersons().contains(person));
        assertEquals(journey.getPlaces().size(), places.size());
        assertEquals(journey.getPersons().size(), persons.size());
    }

    @Test
    public void total_costs_correct_with_same_currency() throws DuplicatePlaceException, DuplicateExpenseException {
        //Given
        Journey journey = InstanceHelper.createJourney();
        Place place1 = InstanceHelper.createPlace(LocalDateTime.now());
        Place place2 = InstanceHelper.createPlace(LocalDateTime.now().plusDays(1));
        Expense expense1 = InstanceHelper.createExpense(Currency.getInstance("EUR"));
        Expense expense2 = InstanceHelper.createExpense(Currency.getInstance("EUR"));
        Expense expense3 = InstanceHelper.createExpense(Currency.getInstance("EUR"));
        Expense expense4 = InstanceHelper.createExpense(Currency.getInstance("EUR"));

        //When
        journey.addPlace(place1);
        journey.addPlace(place2);
        place1.addExpense(expense1);
        place1.addExpense(expense2);
        place2.addExpense(expense3);
        place2.addExpense(expense4);

        Money totalMoney = new Money(expense1.getPrice().getCurrency(), expense1.getPrice().getValue());
        totalMoney.add(new Money(expense2.getPrice().getCurrency(), expense2.getPrice().getValue()));
        totalMoney.add(new Money(expense3.getPrice().getCurrency(), expense3.getPrice().getValue()));
        totalMoney.add(new Money(expense4.getPrice().getCurrency(), expense4.getPrice().getValue()));
        Money costs = journey.totalCost(Currency.getInstance("EUR"));

        //Then
        assertEquals(totalMoney.getValue(), costs.getValue());
    }

    @Test
    public void total_costs_person_correct_with_different_currency() throws DuplicatePlaceException, DuplicateExpenseException {
        //Given
        Journey journey = InstanceHelper.createJourney();
        Place place1 = InstanceHelper.createPlace(LocalDateTime.now());
        Place place2 = InstanceHelper.createPlace(LocalDateTime.now().plusDays(1));
        Expense expense1 = InstanceHelper.createExpense(Currency.getInstance("EUR"));
        Expense expense2 = InstanceHelper.createExpense(Currency.getInstance("USD"));
        Expense expense3 = InstanceHelper.createExpense(Currency.getInstance("EUR"));
        Expense expense4 = InstanceHelper.createExpense(Currency.getInstance("USD"));

        //When
        journey.addPlace(place1);
        journey.addPlace(place2);
        place1.addExpense(expense1);
        place1.addExpense(expense2);
        place2.addExpense(expense3);
        place2.addExpense(expense4);

        Money totalMoney = new Money(expense1.getPrice().getCurrency(), expense1.getPrice().getValue());
        totalMoney.add(expense2.getPrice());
        totalMoney.add(expense3.getPrice());
        totalMoney.add(expense4.getPrice());
        Money costs = journey.totalCost(Currency.getInstance("EUR"));

        //Then
        assertEquals(totalMoney.getValue(), costs.getValue());
    }

    @Test
    public void journey_add_place() throws DuplicatePlaceException {
        //Given
        Journey journey = InstanceHelper.createJourney();

        //When
        journey.addPlace(InstanceHelper.createPlace());

        //Then
        assertEquals(journey.getPlaces().size(), 1);
    }

    @Test
    public void journey_remove_place() throws DuplicatePlaceException, PlaceNotFoundException {
        //Given
        Journey journey = InstanceHelper.createJourney();
        Place place = InstanceHelper.createPlace(LocalDateTime.now());

        //When
        journey.addPlace(place);
        journey.removePlace(place);

        //Then
        assertEquals(journey.getPlaces().size(), 0);
    }

    @Test
    public void journey_get_place() throws DuplicatePlaceException {
        //Given
        Journey journey = InstanceHelper.createJourney();
        Place place = InstanceHelper.createPlace(LocalDateTime.now());

        //When
        journey.addPlace(place);
        List<Place> places = journey.getPlace(place.getName());

        //Then
        assertEquals(places.size(), 1);
        // TODO Warning:(149, 50) 'Optional.get()' without 'isPresent()' check
        assertEquals(places.stream().findFirst().get(), place);
    }

    @Test
    public void journey_remove_place_should_throw_place_not_found()  {
        //Given
        Journey journey = InstanceHelper.createJourney();
        Place place = InstanceHelper.createPlace(LocalDateTime.now());
        place.setName("Blubber");

        //When
        // TODO Warning:(177, 81) Statement lambda can be replaced with expression lambda
        Exception exception = assertThrows(PlaceNotFoundException.class, () -> {
            journey.removePlace(place);
        });

        //Then
        assertTrue(exception.getMessage().contains("Place 'Blubber' does not exist."));
    }

    @Test
    public void journey_add_place_should_throw_duplicate_place() throws DuplicatePlaceException {
        //Given
        Journey journey = InstanceHelper.createJourney();
        Place place = InstanceHelper.createPlace();
        place.setName("Blubber");

        //When
        journey.addPlace(place);
        // TODO Warning:(177, 81) Statement lambda can be replaced with expression lambda
        Exception exception = assertThrows(DuplicatePlaceException.class, () -> {
            journey.addPlace(place);
        });

        //Then
        assertTrue(exception.getMessage().contains("Place 'Blubber' does already exist."));
    }
}

