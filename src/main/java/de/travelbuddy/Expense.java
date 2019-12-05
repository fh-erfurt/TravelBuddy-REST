package de.travelbuddy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;


// Kosten können momentan nur zu gleichen Teilen aufgeteilt werden!!!
public class Expense {

    private String title;
    private String description;
    private Money price;
    private List <Person> involvedPersons;
    private planned status;

    enum planned
    {
        PLANNED,ISSUED,CANCELED
    }

    public Expense(String title, String description, Money price,
                   List<Person> involvedPersons, planned status) {

        this.title = title;
        this.description = description;
        this.price = price;
        this.involvedPersons = involvedPersons;
        this.status = status;
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


    public Money GetMoneyPerPerson(){

        if (involvedPersons.size()!=0)
            return new Money(price.getCurrency(), this.price.getValue().divide(new BigDecimal(involvedPersons.size()), RoundingMode.UNNECESSARY));


        else
            return new Money(price.getCurrency(), this.price.getValue()); // Exception werfen? -> Keine Person eingetragen.
    }
}
