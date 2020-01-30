package de.travelbuddy.journey;

import de.travelbuddy.finance.Expense;
import de.travelbuddy.finance.Money;
import de.travelbuddy.place.Place;
import de.travelbuddy.utilities.InstanceHelper;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JourneyTest {
    @Test
    public void total_costs_correct_with_same_currency() {
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
    public void total_costs_person_correct_with_different_currency() {
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
}

