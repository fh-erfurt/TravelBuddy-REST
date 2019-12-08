package de.travelbuddy.place;
import de.travelbuddy.*;
import de.travelbuddy.finance.Money;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlaceTest {
    @Test
    public void total_costs_correct_with_same_currency() {
        //Given
        ContactDetails contact = new ContactDetails("9999999", "max@musterman.de", "Erfurt",
                "Altonaer Str.", 25, "99086", "Germany" );
        Place place = new Sight("total_costs_test_place", new Coordinates("", ""), contact,
                LocalDateTime.now(), LocalDateTime.now().plusHours(5), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), false);

        LocalDate bday = LocalDate.of(1990,5,2);
        ContactDetails contact2 = new ContactDetails("9999999", "max@musterman.de", "Erfurt",
                "Altonaer Str.", 25, "99086", "Germany" );

        Person person = new Person("Marcel", "van der Heide", bday, contact);
        Expense expense1 = new Expense("Essen", "Pommes", new Money(Currency.getInstance("EUR"),
                new BigDecimal(35)), new ArrayList<>(Arrays.asList(person)), Expense.planned.ISSUED, false );
        Expense expense2 = new Expense("Trinken", "Bier", new Money(Currency.getInstance("EUR"),
                new BigDecimal(55)), new ArrayList<>(Arrays.asList(person)), Expense.planned.ISSUED, false );


        //When
        place.addExpense(expense1);
        place.addExpense(expense2);
        Money totalMoney = new Money(expense1.getPrice().getCurrency(), expense1.getPrice().getValue());
        totalMoney.add(expense2.getPrice());

        //Then
        assertEquals(place.TotalCost(Currency.getInstance("EUR")).getValue(), totalMoney.getValue());
    }

    @Test
    public void total_costs_person_correct_with_same_currency() {
        //Given
        ContactDetails contact = new ContactDetails("9999999", "max@musterman.de", "Erfurt",
                "Altonaer Str.", 25, "99086", "Germany" );
        Place place = new Sight("total_costs_test_place", new Coordinates("", ""), contact,
                LocalDateTime.now(), LocalDateTime.now().plusHours(5), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), false);

        LocalDate bday = LocalDate.of(1990,5,2);
        LocalDate bday2 = LocalDate.of(1990,5,2);
        ContactDetails contact2 = new ContactDetails("9999999", "max@musterman.de", "Erfurt",
                "Altonaer Str.", 25, "99086", "Germany" );

        Person person = new Person("Marcel", "van der Heide", bday, contact);
        Person person2 = new Person("Frieder", "Ullmann", bday, contact);
        Expense expense1 = new Expense("Essen", "Pommes", new Money(Currency.getInstance("EUR"),
                new BigDecimal(35)), new ArrayList<>(Arrays.asList(person)), Expense.planned.ISSUED, false );
        Expense expense2 = new Expense("Trinken", "Bier", new Money(Currency.getInstance("EUR"),
                new BigDecimal(55)), new ArrayList<>(Arrays.asList(person2)), Expense.planned.ISSUED, false );


        //When
        place.addExpense(expense1);
        place.addExpense(expense2);
        Money totalMoney = new Money(expense1.getPrice().getCurrency(), expense1.getPrice().getValue());
        totalMoney.add(expense2.getPrice());

        //Then
        assertEquals(place.TotalCostOfPerson(Currency.getInstance("EUR"), person).getValue(), expense1.getPrice().getValue());
        assertEquals(place.TotalCostOfPerson(Currency.getInstance("EUR"), person2).getValue(), expense2.getPrice().getValue());
    }

    //Todo Test with multiple persons involved!
}