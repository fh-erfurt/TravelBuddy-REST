package de.travelbuddy;

import java.util.List;

public class Journey {

    private static int IDCounter;

    private int journeyID;
    private String title;
    private List<Place> places; //Unterscheiden zwischen accomodations und sights
    private List<Person> persons;
    private List<Expense> expenses;

    public Journey(int journeyID, String title, List<Place> places, List<Person> persons, List<Expense> expenses) {
        IDCounter++;
        this.journeyID = journeyID;
        this.title = title;
        this.places = places;
        this.persons = persons;
        this.expenses = expenses;
    }

    public static int getIDCounter() {
        return IDCounter;
    }

    public int getJourneyID() {
        return journeyID;
    }

    public void setJourneyID(int journeyID) {
        this.journeyID = journeyID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
