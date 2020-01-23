package de.travelbuddy;

import de.travelbuddy.finance.Money;
import de.travelbuddy.place.Place;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

//TODO remove Methoden erstellen (Place und Person), getMethoden für einzelne places/personen erstellen (brauchen wir überhaupt einen getter für die komplette Liste?)
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

    /**
     * Calculate the total costs for the journey
     * @param currency Currency in which to costs should be converted
     * @return The calculated total costs in Money
     */
    public Money TotalCost(Currency currency) {
        Money total = new Money(currency, new BigDecimal(0));

        places.forEach((n) -> total.add(n.TotalCost(currency)));

        return total;
    }

    /**
     * Calculate the total costs for the given person for the journey
     * @param currency Currency in which to costs should be converted
     * @param person The person dor which the costs should be calculated
     * @return The calculated total costs in Money
     */
    public Money TotalCostOfPerson(Currency currency,Person person) {
        Money total = new Money(currency, new BigDecimal(0));

        places.forEach((n) -> total.add(n.TotalCostOfPerson(currency, person)));

        return total;
    }

    /**
     * Search for a person with the given name
     * @param name Name of the person
     * @return
     * @throws IllegalArgumentException
     */
    public Optional<Person> getPerson(String name) throws IllegalArgumentException {
        return persons.stream().filter(person -> person.getName().equals(name)).findFirst();
    }

    /**
     * Search for a place with the given name.
     * @param name Name of the place
     * @return Returns Optional<Place>, check with isPresent()
     */
    public Optional<Place> getPlace(String name)
    {
        return getPlace(name, Place.class);
    }

    /**
     * Search for a place of the given type and with the given name.
     * @param <T>
     * @param name Name of the place
     * @param type Type of the place
     * @return Returns Optional<Place>, check with isPresent()
     */
    public  <T extends Place> Optional<Place> getPlace(String name, Class<T> type) {
        return places.stream()
                    .filter(place -> place.getClass().isAssignableFrom(type))
                    .filter(place -> place.getName().equals(name))
                    .findFirst();
    }

}
