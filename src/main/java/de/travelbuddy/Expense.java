package de.travelbuddy;

import java.util.List;

public class Expense {

    private int expenseID;
    private String title;
    private String description;
    private double price;
    private String currency;
    private boolean perPerson;
    private List <Person> involvedPersons;

    enum planned
    {
        PLANNED,ISSUED,CANCELED;
    }
}
