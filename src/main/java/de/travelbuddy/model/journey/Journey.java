package de.travelbuddy.model.journey;

import de.travelbuddy.model.BaseModel;
import de.travelbuddy.model.DuplicatePersonException;
import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.finance.Money;
import de.travelbuddy.model.place.Place;
import de.travelbuddy.model.place.exception.DuplicatePlaceException;
import de.travelbuddy.model.place.exception.PlaceNotFoundException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Class which represents a Journey
 */

//TODO Add/Remove Expense seems to be missing

@Entity
@Table(name = "JOURNEY")
@Getter @Setter
public class Journey extends BaseModel {

    private String title;

    @OneToMany
    private List<Place> places = new ArrayList<>();

    @OneToMany
    private List<Person> persons = new ArrayList<>();

    @OneToMany
    private Map<String, Expense> expenses = new HashMap<String, Expense>();

    // Required for JPA
    public Journey() {};

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
     * Adds new places to this journey
     * @param places The places to add
     * @throws IllegalArgumentException When a place already exists in this journey
     */
    public void addPlaces(ArrayList<Place> places) throws DuplicatePlaceException
    {
        for (Place p: places) {
            addPlace(p);
        }
    }

    /**
     * Adds a new person to this journey
     * @param newPerson The person to add
     * @throws DuplicatePersonException When the person already exists in this journey
     */
    public void addPerson(Person newPerson) throws DuplicatePersonException {
        if (this.persons.contains(newPerson))
            throw new DuplicatePersonException("Person does already exist.");

        persons.add(newPerson);
    }

    /**
     * Adds new persons to this journey
     * @param persons The persons to add
     * @throws DuplicatePersonException When a person already exists in this journey
     */
    public void addPersons(ArrayList<Person> persons) throws DuplicatePersonException {

        for (Person p: persons) {
            addPerson(p);
        }
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
        Money total = new Money();
        total.setCurrency(currency);
        total.setValue(new BigDecimal(0));

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
        Money total = new Money();
        total.setCurrency(currency);
        total.setValue(new BigDecimal(0));

        places.forEach((n) -> total.add(n.totalCostOfPerson(currency, person)));

        return total;
    }

    /**
     * Search for a person with the given name
     * @param name Name of the person
     * @return All persons with the given name
     */
    public List<Person> findPerson(String name) {
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
    public List<Place> findPlace(String name)
    {
        return findPlace(name, Place.class);
    }

    /**
     * Search for a place of the given type and with the given name.
     * @param <T> Class of the place
     * @param name Name of the place
     * @param type Type of the place
     * @return All places with the given name and type
     */
    public  <T extends Place> List<Place> findPlace(String name, Class<T> type) {
        return places.stream()
                    .filter(place -> type.equals(place.getClass()))
                    .filter(place -> place.getName().equals(name))
                    .collect(Collectors.toList());
    }

}
