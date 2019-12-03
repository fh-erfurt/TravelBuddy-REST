package de.travelbuddy;

import java.util.List;

public class Journey {

    private String title;
    private List<Place> places; //Unterscheiden zwischen accomodations und sights
    private List<Person> persons;
    private List<Expense> expenses;

    public Journey(String title, List<Place> places, List<Person> persons, List<Expense> expenses) {
        this.title = title;
        this.places = places;
        this.persons = persons;
        this.expenses = expenses;
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

    public void addPerson(Person newPerson) {
        persons.add(newPerson);
    }


}
