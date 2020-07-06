package de.travelbuddy.model.finance;

import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.exception.MissingPersonToDivideException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


// Kosten können momentan nur zu gleichen Teilen aufgeteilt werden!!!
@Entity
@Table(name = "EXPENSE")
@Getter @Setter
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Transient
    private Money price;

    @OneToMany
    private List<Person> involvedPersons;
    private planned status;
    private boolean perPerson;

    public enum planned
    {
        PLANNED,ISSUED,CANCELED
    }

    // Required for JPA
    public Expense() {};

    public Expense(String title, String description, Money price,
                   List<Person> involvedPersons, planned status, boolean perPerson) {

        this.title = title;
        this.description = description;
        this.price = price;
        this.involvedPersons = involvedPersons;
        this.status = status;
        this.perPerson = perPerson;
    }

    public boolean getPerPerson() {return perPerson;}
    public void setPerPerson(boolean perPerson) {this.perPerson = perPerson;}


    /**
     * Get the Money per Person
     * @return the money value, with the currency
     * @throws MissingPersonToDivideException there is no person to divide
     */
    public Money getMoneyPerPerson() throws MissingPersonToDivideException {

        if (involvedPersons.size()!=0)
            return new Money(price.getCurrency(), this.price.getValue().divide(BigDecimal.valueOf(involvedPersons.size()),2, RoundingMode.HALF_UP));
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
