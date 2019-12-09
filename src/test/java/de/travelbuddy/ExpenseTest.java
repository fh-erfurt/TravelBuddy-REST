package de.travelbuddy;
import static org.junit.jupiter.api.Assertions.*;

import de.travelbuddy.finance.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class ExpenseTest {
    @Test
    public void  should_create_expense() {

        String title = "Curry Wurscht";
        String description = "Curry mit Wurscht für alle";
        Money price = new Money(Currency.getInstance("EUR"), new BigDecimal(3.50));
        List<Person> involvedPersons = new ArrayList<>();
        Expense.planned status = Expense.planned.PLANNED;
        boolean perPerson = true;

        Expense expense = new Expense(title, description, price, involvedPersons, status, true);

        assertEquals(expense.getTitle(), title);
        assertEquals(expense.getDescription(), description);
        assertEquals(expense.getPrice(), price);
        assertEquals(expense.getInvolvedPersons(), involvedPersons);
        assertEquals(expense.getStatus(), status);
        assertEquals(expense.getPerPerson(), true);
    }
}


