package de.travelbuddy;

import java.util.Map;

public class JourneyManager {
    private Map<String, Journey> journeys;

    public void AddJourney(Journey journey) throws IllegalArgumentException {
        if (journeys.containsKey(journey.getTitle()))
            throw new IllegalArgumentException(String.format("Journey with the title '%s' already exists", journey.getTitle()));

        journeys.put(journey.getTitle(), journey);
    }

    public Journey RemoveJourney(Journey journey) throws IllegalArgumentException {
        if (!journeys.containsKey(journey.getTitle()))
            throw new IllegalArgumentException(String.format("Journey with the title '%s' does not exists", journey.getTitle()));

        return journeys.remove(journey.getTitle());
    }

    public Journey get(String name) throws IllegalArgumentException {
        if (!journeys.containsKey(name))
            throw new IllegalArgumentException(String.format("Journey with the title '%s' does not exists", name));

        return journeys.get(name);
    }
}
