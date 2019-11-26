package de.travelbuddy;

import java.util.List;

public class Expense {

    private static int IDCounter;

    private int expenseID;
    private String title;
    private String description;
    private double price;
    private String currency;
    private boolean perPerson;
    private List <Person> involvedPersons;
    private planned status;

    enum planned
    {
        PLANNED,ISSUED,CANCELED
    }

    public Expense(int expenseID, String title, String description, double price, String currency, boolean perPerson, List<Person> involvedPersons, planned status) {

        IDCounter++;
        this.expenseID = IDCounter;
        this.title = title;
        this.description = description;
        this.price = price;
        this.currency = currency;
        this.perPerson = perPerson;
        this.involvedPersons = involvedPersons;
        this.status = status;
    }

    public static int getIDCounter() {
        return IDCounter;
    }

    public int getExpenseID() {
        return expenseID;
    }

    public void setExpenseID(int expenseID) {
        this.expenseID = expenseID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isPerPerson() {
        return perPerson;
    }

    public void setPerPerson(boolean perPerson) {
        this.perPerson = perPerson;
    }

    public List<Person> getInvolvedPersons() {
        return involvedPersons;
    }

    public void setInvolvedPersons(List<Person> involvedPersons) {
        this.involvedPersons = involvedPersons;
    }

    public planned getStatus() {
        return status;
    }

    public void setStatus(planned status) {
        this.status = status;
    }
}
