package de.travelbuddy.journey;

import de.travelbuddy.Person;
import de.travelbuddy.finance.Money;

import java.math.BigDecimal;
import java.util.*;

public class JourneyManager {
    private Map<String, Journey> journeys = new HashMap<String, Journey>();

    public JourneyManager()
    {
    }

    public int journeyCount()
    {
        return journeys.keySet().size();
    }

    public void addJourney(Journey journey) throws DuplicateJourneyException {
        if (journeys.containsKey(journey.getTitle()))
            throw new DuplicateJourneyException(String.format("Journey with the title '%s' already exists", journey.getTitle()));

        journeys.put(journey.getTitle(), journey);
    }

    public Journey removeJourney(Journey journey) throws JourneyNotFoundException {
        if (!journeys.containsKey(journey.getTitle()))
            throw new JourneyNotFoundException(String.format("Journey with the title '%s' does not exists", journey.getTitle()));

        return journeys.remove(journey.getTitle());
    }

    public Journey getJourney(String name) throws JourneyNotFoundException {
        if (!journeys.containsKey(name))
            throw new JourneyNotFoundException(String.format("Journey with the title '%s' does not exists", name));

        return journeys.get(name);
    }

    public List<String> getJourneyNames()
    {
        return new ArrayList<>(journeys.keySet());
    }


    /**
     * Calculate the total costs for all journeys
     * @param currency Currency in which to costs should be converted
     * @return The calculated total costs in Money
     */
    public Money totalCost(Currency currency) {
        Money total = new Money(currency, new BigDecimal(0));

        journeys.forEach((i, n) -> total.add(n.totalCost(currency)));

        return total;
    }

    /**
     * Calculate the total costs for the given person for all journeys
     * @param currency Currency in which to costs should be converted
     * @param person The person dor which the costs should be calculated
     * @return The calculated total costs in Money
     */
    public Money totalCostOfPerson(Currency currency, Person person) {
        Money total = new Money(currency, new BigDecimal(0));

        journeys.forEach((i, n) -> total.add(n.totalCostOfPerson(currency, person)));

        return total;
    }
}