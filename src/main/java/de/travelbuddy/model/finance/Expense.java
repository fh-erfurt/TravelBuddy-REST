package de.travelbuddy.model.finance;

import de.travelbuddy.model.BaseModel;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.exception.MissingPersonToDivideException;
import de.travelbuddy.storage.core.IJpaGenericStream;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


// Kosten können momentan nur zu gleichen Teilen aufgeteilt werden!!!
@Entity
@Table(name = "EXPENSE")
@Getter @Setter
public class Expense extends BaseModel {

    private String title;
    private String description;
    private planned status;
    private boolean perPerson;

    @OneToMany
    private List<Person> involvedPersons = new ArrayList<>();

    @Transient
    private Money price;
    @Transient
    private final IJpaGenericStream<Expense> expenseStream = null;

    public enum planned
    {
        PLANNED,ISSUED,CANCELED
    }

    // Required for JPA
    public Expense() {
    };

    /**
     * Get the Money per Person
     * @return the money value, with the currency
     * @throws MissingPersonToDivideException there is no person to divide
     */
    public Money getMoneyPerPerson() throws MissingPersonToDivideException {

        if (involvedPersons.size()!=0) {
            Money mon = new Money();
            mon.setCurrency(price.getCurrency());
            mon.setValue(this.price.getValue().divide(BigDecimal.valueOf(involvedPersons.size()), 2, RoundingMode.HALF_UP));
            return mon;
        }
        else
            throw new MissingPersonToDivideException("No Persons to divide Expense between");
    }

    /**
     * Create a new person
     * @param person to add
     * @throws IllegalArgumentException if the Person already exist
     */
    public void addPerson(Person person) throws IllegalArgumentException {

        if (isInvolved(person)) {
            throw new IllegalArgumentException("Person already added.");
        }

        involvedPersons.add(person);
    }

    /**
     * delete a person
     * @param person to remove
     * @throws IllegalArgumentException if the person doesn't exist
     */
    public void removePerson(Person person) throws IllegalArgumentException {
        if (!isInvolved(person)) {
            throw new IllegalArgumentException("Person does not exist.");
        }

        involvedPersons.remove(person);
    }

    /**
     * Is the person involved
     * @param person is involved in some journey
     * @return true / false if the person is involved or not
     */
    public boolean isInvolved(Person person) {
        return involvedPersons.contains(person);
    }
}