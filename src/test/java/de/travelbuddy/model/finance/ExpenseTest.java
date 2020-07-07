package de.travelbuddy.model.finance;
import static org.junit.jupiter.api.Assertions.*;

import de.travelbuddy.model.ContactDetails;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.exception.MissingPersonToDivideException;
import de.travelbuddy.utilities.InstanceHelper;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class ExpenseTest {

    @Test
    public void  should_create_expense() {

        Expense expense = createExampleExpense1();

        assertEquals(expense.getTitle(), "Curry Wurscht");
        assertEquals(expense.getDescription(), "Curry mit Wurscht für alle");
        assertEquals(expense.getPrice(), expense.getPrice());
        assertEquals(expense.getInvolvedPersons(), new ArrayList<>());
        assertEquals(expense.getStatus(), Expense.planned.PLANNED);
        assertTrue(expense.isPerPerson());
    }

    @Test
    public void should_add_person_to_expense() {

        //Given
        Expense expense = createExampleExpense1();
        Person Marcel = InstanceHelper.createPerson();
        Person Tim = InstanceHelper.createPerson();


        //When
        expense.addPerson(Tim);
        expense.addPerson(Marcel);

        //Then
        assertTrue(expense.getInvolvedPersons().contains(Tim));
        assertTrue(expense.getInvolvedPersons().contains(Marcel));

    }

    @Test
    public void get_money_per_person_should_divide_right () throws MissingPersonToDivideException {

        //Given
        Expense expense = createExampleExpense1();
        Person Marcel = InstanceHelper.createPerson();
        Person Tim = InstanceHelper.createPerson();
        Money startMoney = new Money();
        startMoney.setCurrency(null);
        startMoney.setValue(BigDecimal.valueOf(30).setScale(2,RoundingMode.HALF_UP));
        Money dividedMoney = new Money();
        dividedMoney.setCurrency(null);
        dividedMoney.setValue(null);
        Exception exception = null;

        expense.setPrice(startMoney);
        expense.addPerson(Marcel);
        expense.addPerson(Tim);

        //When
        dividedMoney = expense.getMoneyPerPerson();

        //Then
        assertEquals(BigDecimal.valueOf(15).setScale(2,RoundingMode.HALF_UP),dividedMoney.getValue());

    }

    @Test
    public void get_money_per_person_should_throw_exception (){

        //Given
        Expense expense = createExampleExpense1();
        Money startMoney = new Money();
        startMoney.setCurrency(null);
        startMoney.setValue(BigDecimal.valueOf(30).setScale(2,RoundingMode.HALF_UP));

        expense.setPrice(startMoney);

        //When
        Exception exception = assertThrows(MissingPersonToDivideException.class, expense::getMoneyPerPerson);

        //Then
        assertTrue(exception.getMessage().contains("No Persons to divide Expense between"));

    }

    public Expense createExampleExpense1 (){

        String title = "Curry Wurscht";
        String description = "Curry mit Wurscht für alle";
        Money price = new Money();
        price.setCurrency(Currency.getInstance("EUR"));
        price.setValue(BigDecimal.valueOf(4).setScale(2, RoundingMode.HALF_UP));
        List<Person> involvedPersons = new ArrayList<>();
        Expense.planned status = Expense.planned.PLANNED;
        boolean perPerson = true;

        Expense exp = new Expense();
        exp.setTitle(title);
        exp.setDescription(description);
        exp.setPrice(price);
        exp.setInvolvedPersons(involvedPersons);
        exp.setStatus(status);
        exp.setPerPerson(true);
        return exp;

    }
}


