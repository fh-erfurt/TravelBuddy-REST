package de.travelbuddy;

import de.travelbuddy.finance.Money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


// Kosten können momentan nur zu gleichen Teilen aufgeteilt werden!!!
public class Expense {

    private String title;
    private String description;
    private Money price;
    private List<Person> involvedPersons;
    private planned status;
    private boolean perPerson;

    public enum planned
    {
        PLANNED,ISSUED,CANCELED
    }

    public Expense(String title, String description, Money price,
                   List<Person> involvedPersons, planned status, boolean perPerson) {

        this.title = title;
        this.description = description;
        this.price = price;
        this.involvedPersons = involvedPersons;
        this.status = status;
        this.perPerson = perPerson;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public Money getPrice() {return price;}

    public void setPrice(Money price) {this.price = price;}

    public List<Person> getInvolvedPersons() {return involvedPersons;}

    public void setInvolvedPersons(List<Person> involvedPersons) {this.involvedPersons = involvedPersons;}

    public planned getStatus() {return status;}

    public void setStatus(planned status) {this.status = status;}

    public boolean getPerPerson() {return perPerson;}

    public void setPerPerson(boolean perPerson) {this.perPerson = perPerson;}

    public Money getMoneyPerPerson(){

        if (involvedPersons.size()!=0)
            return new Money(price.getCurrency(), this.price.getValue().divide(BigDecimal.valueOf(involvedPersons.size()),2, RoundingMode.HALF_UP));
        else
            return new Money(price.getCurrency(), this.price.getValue()); // Exception werfen? -> Keine Person eingetragen.
    }

    public void addPerson(Person person) throws IllegalArgumentException { //Todo check if person is in journey

        if (isInvolved(person)) {
            throw new IllegalArgumentException("Person already added.");
        }

        involvedPersons.add(person);
    }

    public void removePerson(Person person) throws IllegalArgumentException {
        if (!isInvolved(person)) {
            throw new IllegalArgumentException("Person does not exist.");
        }

        involvedPersons.remove(person);
    }

    public boolean isInvolved(Person person) {
        return involvedPersons.contains(person); //Todo Langsam? Objekt wird hier vermutlich komplett geprüft da kein Key vorhanden
    }
}
