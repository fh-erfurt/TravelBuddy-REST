package de.travelbuddy.place;

import de.travelbuddy.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SightTest {
    @Test
    public void correctly_instantiate_Sight_Louvre() {

        String name = "Louvre";
        Coordinates coordinates = new Coordinates("48.864824", "2.334595");
        ContactDetails contactDetails = new ContactDetails("+33 1 40 20 50 50", "louvre@paris.fr",
                "Paris", "Rue de Rivoli", 1, "75001", "Frankreich");
        LocalDateTime arrive = LocalDateTime.of(2020,7,25,16,45);
        LocalDateTime departure = LocalDateTime.of(2020,7,28,20,5);
        List<Expense> expenses = new ArrayList<>();
        List<Connection> connectionsToNextPlace = new ArrayList<>();
        List<Person> involvedPersons = new ArrayList<>();
        boolean indoor = true;

        Sight sight = new Sight(name, coordinates, contactDetails, arrive, departure, expenses, connectionsToNextPlace,
                involvedPersons, true);

        assertEquals(sight.getName(), name);
        assertEquals(sight.getCoordinates(), coordinates);
        assertEquals(sight.getContactDetails(), contactDetails);
        assertEquals(sight.getArrive(), arrive);
        assertEquals(sight.getDeparture(), departure);
        assertEquals(sight.getExpenses(), expenses);
        assertEquals(sight.getConnectionsToNextPlace(), connectionsToNextPlace);
        assertEquals(sight.getInvolvedPersons(), involvedPersons);
        assertEquals(sight.isIndoor(), true);
    }

    @Test
    public void correctly_instantiate_Sight_TowerOfLondinium() {

        String name = "Tower of London";
        Coordinates coordinates = new Coordinates("51.508112", "-0.075949");
        ContactDetails contactDetails = new ContactDetails(" +44 20 3166 6000", "tower@london.uk",
                "London", "St Katharine's & Wapping", 1, "EC3N 4AB", "Vereinigtes Königreich");
        LocalDateTime arrive = LocalDateTime.of(2021,1,21,8,45);
        LocalDateTime departure = LocalDateTime.of(2021,1,21,15,5);
        List<Expense> expenses = new ArrayList<>();
        List<Connection> connectionsToNextPlace = new ArrayList<>();
        List<Person> involvedPersons = new ArrayList<>();
        boolean indoor = true;

        Sight sight = new Sight(name, coordinates, contactDetails, arrive, departure, expenses, connectionsToNextPlace,
                involvedPersons, true);

        assertEquals(sight.getName(), name);
        assertEquals(sight.getCoordinates(), coordinates);
        assertEquals(sight.getContactDetails(), contactDetails);
        assertEquals(sight.getArrive(), arrive);
        assertEquals(sight.getDeparture(), departure);
        assertEquals(sight.getExpenses(), expenses);
        assertEquals(sight.getConnectionsToNextPlace(), connectionsToNextPlace);
        assertEquals(sight.getInvolvedPersons(), involvedPersons);
        assertEquals(sight.isIndoor(), true);
    }

    @Test
    public void correctly_instantiate_Sight_Kollosseum() {

        String name = "Kollosseum";
        Coordinates coordinates = new Coordinates("41.890210", "12.492231");
        ContactDetails contactDetails = new ContactDetails(" +39 67 45 67 23", "kollosseum@roma.it",
                "Rom", "Piazza del Colosseo", 1, "00184", "Italien");
        LocalDateTime arrive = LocalDateTime.of(2057,7,1,13,0);
        LocalDateTime departure = LocalDateTime.of(2057,7,4,20,5);
        List<Expense> expenses = new ArrayList<>();
        List<Connection> connectionsToNextPlace = new ArrayList<>();
        List<Person> involvedPersons = new ArrayList<>();
        boolean indoor = false;

        Sight sight = new Sight(name, coordinates, contactDetails, arrive, departure, expenses, connectionsToNextPlace,
                involvedPersons, true);

        assertEquals(sight.getName(), name);
        assertEquals(sight.getCoordinates(), coordinates);
        assertEquals(sight.getContactDetails(), contactDetails);
        assertEquals(sight.getArrive(), arrive);
        assertEquals(sight.getDeparture(), departure);
        assertEquals(sight.getExpenses(), expenses);
        assertEquals(sight.getConnectionsToNextPlace(), connectionsToNextPlace);
        assertEquals(sight.getInvolvedPersons(), involvedPersons);
        assertEquals(sight.isIndoor(), true);
    }

    @Test
    public void correctly_instantiate_Sight_BasilikaStMichael() {

        String name = "Basilika St. Michael";
        Coordinates coordinates = new Coordinates("39.567425", "2.648299");
        ContactDetails contactDetails = new ContactDetails(" +34 971 71 54 55", "basilika@palma.es",
                "Palma", "Carrer de Sant Miquel", 21, "07002", "Illes Balears, Spanien");
        LocalDateTime arrive = LocalDateTime.of(2030,12,6,9,25);
        LocalDateTime departure = LocalDateTime.of(2030,12,7,1,17);
        List<Expense> expenses = new ArrayList<>();
        List<Connection> connectionsToNextPlace = new ArrayList<>();
        List<Person> involvedPersons = new ArrayList<>();
        boolean indoor = true;

        Sight sight = new Sight(name, coordinates, contactDetails, arrive, departure, expenses, connectionsToNextPlace,
                involvedPersons, true);

        assertEquals(sight.getName(), name);
        assertEquals(sight.getCoordinates(), coordinates);
        assertEquals(sight.getContactDetails(), contactDetails);
        assertEquals(sight.getArrive(), arrive);
        assertEquals(sight.getDeparture(), departure);
        assertEquals(sight.getExpenses(), expenses);
        assertEquals(sight.getConnectionsToNextPlace(), connectionsToNextPlace);
        assertEquals(sight.getInvolvedPersons(), involvedPersons);
        assertEquals(sight.isIndoor(), true);
    }

    @Test
    public void correctly_instantiate_Sight_TierparkBerlin() {

        String name = "Tierpark Berlin";
        Coordinates coordinates = new Coordinates("52.502642", "13.531345");
        ContactDetails contactDetails = new ContactDetails(" +49 (030) 515 31-0.", "info@tierpark-berlin.de",
                "Berlin", "Am Tierpark", 125, "10319", "Deutschland");
        LocalDateTime arrive = LocalDateTime.of(2010,6,30,9,25);
        LocalDateTime departure = LocalDateTime.of(2012,6,7,1,17);
        List<Expense> expenses = new ArrayList<>();
        List<Connection> connectionsToNextPlace = new ArrayList<>();
        List<Person> involvedPersons = new ArrayList<>();
        boolean indoor = false;

        Sight sight = new Sight(name, coordinates, contactDetails, arrive, departure, expenses, connectionsToNextPlace,
                involvedPersons, true);

        assertEquals(sight.getName(), name);
        assertEquals(sight.getCoordinates(), coordinates);
        assertEquals(sight.getContactDetails(), contactDetails);
        assertEquals(sight.getArrive(), arrive);
        assertEquals(sight.getDeparture(), departure);
        assertEquals(sight.getExpenses(), expenses);
        assertEquals(sight.getConnectionsToNextPlace(), connectionsToNextPlace);
        assertEquals(sight.getInvolvedPersons(), involvedPersons);
        assertEquals(sight.isIndoor(), true);
    }
}

