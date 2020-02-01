package de.travelbuddy.journey;

import de.travelbuddy.Person;
import de.travelbuddy.finance.Money;
import de.travelbuddy.place.DuplicatePlaceException;
import de.travelbuddy.place.Place;
import de.travelbuddy.place.PlaceNotFoundException;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<Place> getPlaces() { return places; }
    public List<Person> getPersons() { return persons; }
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Adds a new place to this journey
     * @param newPlace The place to add
     * @throws IllegalArgumentException When the place already exists in this journey
     */
    public void addPlace(Place newPlace) throws DuplicatePlaceException {
        if (this.places.contains(newPlace))
            throw new DuplicatePlaceException(String.format("Place '%s' does already exist.", newPlace.getName()));

        places.add(newPlace);
    }

    /**
     * Adds a new person to this journey
     * @param newPerson The person to add
     * @throws IllegalArgumentException When the person already exists in this journey
     */
    public void addPerson(Person newPerson) throws IllegalArgumentException {
        if (this.persons.contains(newPerson))
            throw new IllegalArgumentException("Person does already exist.");

        persons.add(newPerson);
    }

    /**
     * Removes the given place from this journey
     * @param place The place to remove
     * @throws IllegalArgumentException When the place does not exist in this journey
     */
    public void removePlace(Place place) throws PlaceNotFoundException {
        if (!this.places.contains(place))
            throw new PlaceNotFoundException(String.format("Place '%s' does not exist.", place.getName()));

        places.remove(place);
    }

    /**
     * Removes the given person from this journey
     * @param person The person to reemove
     * @throws IllegalArgumentException When the person does not exist in this journey
     */
    public void removePerson(Person person) throws IllegalArgumentException {
        if (!this.persons.contains(person))
            throw new IllegalArgumentException("Person does not exist.");

        persons.remove(person);
    }

    /**
     * Calculate the total costs for the journey
     * @param currency Currency in which to costs should be converted
     * @return The calculated total costs in Money
     */
    public Money totalCost(Currency currency) {
        Money total = new Money(currency, new BigDecimal(0));

        places.forEach((n) -> total.add(n.totalCost(currency)));

        return total;
    }

    /**
     * Calculate the total costs for the given person for the journey
     * @param currency Currency in which to costs should be converted
     * @param person The person dor which the costs should be calculated
     * @return The calculated total costs in Money
     */
    public Money totalCostOfPerson(Currency currency, Person person) {
        Money total = new Money(currency, new BigDecimal(0));

        places.forEach((n) -> total.add(n.totalCostOfPerson(currency, person)));

        return total;
    }

    /**
     * Search for a person with the given name
     * @param name Name of the person
     * @return All persons with the given name
     */
    public List<Person> getPerson(String name) {
        return persons
                .stream()
                .filter(person -> person.getName().equals(name))
                .collect(Collectors.toList());
    }

    /**
     * Search for a place with the given name.
     * @param name Name of the place
     * @return All places with the given name
     */
    public List<Place> getPlace(String name)
    {
        return getPlace(name, Place.class);
    }

    // TODO Warning:(135, 15) '@param <T>' tag description is missing
    /**
     * Search for a place of the given type and with the given name.
     * @param <T>
     * @param name Name of the place
     * @param type Type of the place
     * @return All places with the given name and type
     */
    public  <T extends Place> List<Place> getPlace(String name, Class<T> type) {
        //Extra für Jonas hinzugefügt, damit wir, wie besprochen, eine 1 bekommen
        return places.stream()
                    .filter(place -> place.getClass().isAssignableFrom(type))
                    .filter(place -> place.getName().equals(name))
                    .collect(Collectors.toList());
    }

}
