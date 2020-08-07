package de.travelbuddy.model.finance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import de.travelbuddy.model.BaseModel;
import de.travelbuddy.model.Person;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@SequenceGenerator(sequenceName = "seq_expense", name = "seq_gen_expense")
public class Expense extends BaseModel {

    @Column(nullable = false)
    private String title;
    private String description;

    @Column(columnDefinition = "smallint", nullable = false)
    private planned status = planned.PLANNED;
    private boolean perPerson;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Person> involvedPersons = new ArrayList<>();

    private Money price;

    public enum planned
    {
        PLANNED,ISSUED,CANCELED
    }

    /**
     * Get the Money per Person
     * @return the money value, with the currency
     */
    public Money getMoneyPerPerson() {

        if (involvedPersons.size()!=0) {
            Money mon = new Money();
            mon.setCurrency(price.getCurrency());
            mon.setValue(this.price.getValue().divide(BigDecimal.valueOf(involvedPersons.size()), 2, RoundingMode.HALF_UP));
            return mon;
        }
        return this.price;
    }

    /**
     * Add a new person
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
     * Remove a person
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