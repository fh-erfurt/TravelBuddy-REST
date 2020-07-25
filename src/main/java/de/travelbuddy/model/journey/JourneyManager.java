package de.travelbuddy.model.journey;

import de.travelbuddy.model.Person;
import de.travelbuddy.model.finance.Money;
import de.travelbuddy.model.journey.exception.DuplicateJourneyException;
import de.travelbuddy.model.journey.exception.JourneyNotFoundException;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
public class JourneyManager {

    private Map<Long, Journey> journeys = new HashMap<>();

    public List<String> getJourneyNames()
    {
        return journeys.values().stream().map(Journey::getTitle).collect((Collectors.toList()));
    }

    public int journeyCount()
    {
        return journeys.keySet().size();
    }

    /**
     * Add a new Journey to the manager
     * @param journey which should be added
     * @throws DuplicateJourneyException If there is another journey with the same title
     */
    public void addJourney(Journey journey) throws DuplicateJourneyException {
        if (journeys.containsKey(journey.getId()))
            throw new DuplicateJourneyException(String.format("Journey with the title '%s' already exists", journey.getTitle()));

        journeys.put(journey.getId(), journey);
    }

    /**
     * Deletes a Journey from the manager
     * @param journey Journey which should be removed
     * @return journey The removed journey
     * @throws JourneyNotFoundException If the journey doesn't exists
     */
    public Journey removeJourney(Journey journey) throws JourneyNotFoundException {
        if (!journeys.containsKey(journey.getTitle()))
            throw new JourneyNotFoundException(String.format("Journey with the title '%s' does not exists", journey.getTitle()));

        return journeys.remove(journey.getTitle());
    }

    /**
     * Get the Journey with the given name
     * @param title The name of a journey
     * @return The Journey from the manager
     * @throws JourneyNotFoundException If the journey doesn't exist
     */
    public Journey getJourney(String title) throws JourneyNotFoundException {
        if (!journeys.containsKey(title))
            throw new JourneyNotFoundException(String.format("Journey with the title '%s' does not exists", title));

        return journeys.get(title);
    }




    /**
     * Calculate the total costs for all journeys
     * @param currency Currency in which to costs should be converted
     * @return The calculated total costs in Money
     */
    public Money totalCost(Currency currency) {
        Money total = new Money();
        total.setCurrency(currency);
        total.setValue(new BigDecimal(0));

        journeys.forEach((i, n) -> total.add(n.totalCost(currency)));

        return total;
    }

    /**
     * Calculate the total costs for the given person for all journeys
     * @param currency Currency in which to costs should be converted
     * @param person The person for which the costs should be calculated
     * @return The calculated total costs in Money
     */
    public Money totalCostOfPerson(Currency currency, Person person) {
        Money total = new Money();
        total.setCurrency(currency);
        total.setValue(new BigDecimal(0));

        journeys.forEach((i, n) -> total.add(n.totalCostOfPerson(currency, person)));

        return total;
    }
}