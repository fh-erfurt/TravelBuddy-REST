package de.travelbuddy;

import de.travelbuddy.finance.Money;
import de.travelbuddy.place.Place;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public class Journey {

    private String title;

    private List<Place> places;
    private List<Person> persons;

    public Journey(String title, List<Place> places, List<Person> persons) {
        this.title = title;
        this.places = places;
        this.persons = persons;
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

    public List<Person> getPersons() { return persons; }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public void addPlace(Place newPlace) {
        places.add(newPlace);
    }

    public void addPerson(Person newPerson) {
        persons.add(newPerson);
    }


    public Money TotalCost(Currency currency) {
        Money total = new Money(currency, new BigDecimal(0));

        places.forEach((n) -> total.add(n.TotalCost(currency)));

        return total;
    }

    public Money TotalCostOfPerson(Currency currency,Person person) {
        //Todo implement!
        return new Money(currency, new BigDecimal(0));
    }

}
