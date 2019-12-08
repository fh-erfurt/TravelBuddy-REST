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
    public void total_costs_same_currency() {
        //Given
        ContactDetails contact = new ContactDetails("9999999", "max@musterman.de", "Erfurt",
                "Altonaer Str.", 25, "99086", "Germany" );
        Place place = new Sight("total_costs_test_place", new Coordinates("", ""), contact,
                LocalDateTime.now(), LocalDateTime.now().plusHours(5), new ArrayList<Expense>(),
                new ArrayList<Connection>(), new ArrayList<Person>(), false);

        String firstname = "Marcel";
        String name = "van der Heide";
        LocalDate bday = LocalDate.of(1990,5,2);

        ContactDetails contact2 = new ContactDetails("9999999", "max@musterman.de", "Erfurt",
                "Altonaer Str.", 25, "99086", "Germany" );

        Person person = new Person(firstname, name, bday, contact);
        Expense expense1 = new Expense("Essen", "Pommes", new Money(Currency.getInstance("EUR"),
                new BigDecimal(35)), new ArrayList<Person>(Arrays.asList(person)), Expense.planned.ISSUED, false );
        Expense expense2 = new Expense("Trinken", "Bier", new Money(Currency.getInstance("EUR"),
                new BigDecimal(55)), null, Expense.planned.ISSUED, false );


        //When
        place.addExpense(expense1);
        place.addExpense(expense2);
        Money totalMoney = new Money(expense1.getPrice().getCurrency(), expense1.getPrice().getValue());
        totalMoney.add(expense2.getPrice());

        //Then
        assertEquals(place.TotalCost(Currency.getInstance("EUR")).getValue(), totalMoney.getValue());
    }
}