package de.travelbuddy.journey;

import de.travelbuddy.journey.exception.DuplicateJourneyException;
import de.travelbuddy.journey.exception.JourneyNotFoundException;
import de.travelbuddy.utilities.InstanceHelper;

import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JourneyManagerTest {

    @Test
    public void journeymanager_add_journey() throws DuplicateJourneyException {
        //Given
        JourneyManager manager = new JourneyManager();
        Journey journey = InstanceHelper.createJourney();

        //When
        manager.addJourney(journey);

        //Then
        assertEquals( manager.journeyCount(), 1);
    }

    @Test
    public void journeymanager_remove_journey() throws DuplicateJourneyException, JourneyNotFoundException {
        //Given
        JourneyManager manager = new JourneyManager();
        Journey journey = InstanceHelper.createJourney();

        //When
        manager.addJourney(journey);
        manager.removeJourney(journey);

        //Then
        assertEquals( manager.journeyCount(), 0);
    }

    @Test
    public void journeymanager_get_journey() throws DuplicateJourneyException, JourneyNotFoundException {
        //Given
        JourneyManager manager = new JourneyManager();
        Journey journey = InstanceHelper.createJourney();

        //When
        manager.addJourney(journey);
        Journey sameJourney = manager.getJourney(journey.getTitle());

        //Then
        assertEquals(journey, sameJourney);
    }

    @Test
    public void journeymanager_should_throw_journey_not_found()  {
        //Given
        JourneyManager manager = new JourneyManager();

        //When
        Exception exception = assertThrows(JourneyNotFoundException.class, () -> manager.getJourney("Blubber"));

        //Then
        assertTrue(exception.getMessage().contains("Journey with the title 'Blubber' does not exists"));
    }

    @Test
    public void journeymanager_should_return_all_journey_names() throws DuplicateJourneyException {
        //Given
        JourneyManager manager = new JourneyManager();
        Journey journey1 = InstanceHelper.createJourney();
        Journey journey2 = InstanceHelper.createJourney();
        Journey journey3 = InstanceHelper.createJourney();

        //When
        manager.addJourney(journey1);
        manager.addJourney(journey2);
        manager.addJourney(journey3);
        List<String> names = manager.getJourneyNames();

        //Then
        assertTrue(names.contains(journey1.getTitle()));
        assertTrue(names.contains(journey2.getTitle()));
        assertTrue(names.contains(journey3.getTitle()));
        assertEquals(names.size(), 3);
    }

    @Test
    public void journeymanager_should_throw_duplicate_journey() throws DuplicateJourneyException {
        //Given
        JourneyManager manager = new JourneyManager();
        Journey journey = InstanceHelper.createJourney();

        //When
        manager.addJourney(journey);
        Exception exception = assertThrows(DuplicateJourneyException.class, () -> manager.addJourney(journey));

        //Then
        assertTrue(exception.getMessage().contains(
                String.format("Journey with the title '%s' already exists", journey.getTitle())));
    }
}