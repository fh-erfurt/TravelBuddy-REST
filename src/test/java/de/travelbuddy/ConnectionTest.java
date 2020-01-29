package de.travelbuddy;

import static org.junit.jupiter.api.Assertions.*;

import de.travelbuddy.finance.Money;
import de.travelbuddy.place.Accommodation;
import de.travelbuddy.place.Place;
import de.travelbuddy.place.Sight;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.time.Duration;

public class ConnectionTest {
    @Test
    public void should_create_connectionBerlin() {
        String title = "Berlin";
        LocalDateTime arrive = LocalDateTime.of(2020,7,25,16,45) ;
        LocalDateTime departure = LocalDateTime.of(2020,7,28,20,5) ;
        ContactDetails contact = new ContactDetails("9999999", "max@musterman.de", "Erfurt",
                "Altonaer Str.", 25, "99086", "Germany" );
        Expense expense1 = new Expense("Essen", "Pommes", new Money(Currency.getInstance("EUR"),
                new BigDecimal(35)), new ArrayList<>(), Expense.planned.ISSUED, false );
        Place start = new Accommodation("Generator Hostel",
                new Coordinates("52.516181", "13.376935"),
                contact,
                arrive,departure, new ArrayList<>(Arrays.asList(expense1)), new ArrayList<>(),
                new ArrayList<>(), Accommodation.accommodationType.HOSTEL);

        Place end = new Sight("Brandenburger Tor", new Coordinates("52.516275", "13.377704"),
                new ContactDetails("9999999", "brandeburger@tor.de", "Berlin",
                        "Pariser Platz", 1, "10117", "Deutschland"), arrive, departure,
                new ArrayList<>(Arrays.asList(expense1)), new ArrayList<>(), new ArrayList<>(), false);


        Connection connection = new Connection(title, arrive, departure, start, end, expense1);

        assertEquals(connection.getTitle(), title);
        assertEquals(connection.getArrive(), arrive);
        assertEquals(connection.getDeparture(), departure);
        assertEquals(connection.getStart(), start);
        assertEquals(connection.getEnd(), end);
        assertEquals(connection.getExpense(), expense1);

    }

    @Test
    public void should_create_connectionHarz() {
        String title = "Harz";
        LocalDateTime arrive = LocalDateTime.of(2020,8,13,8,13) ;
        LocalDateTime departure = LocalDateTime.of(2020,8,31,13,8) ;
        ContactDetails contact = new ContactDetails("039455 58817", "INFO@zelten.de", "Elend",
                " Am Stern", 1, "38875", "Germany" );
        Expense expense1 = new Expense("Zelten", "OpenAir", new Money(Currency.getInstance("EUR"),
                new BigDecimal(35)), new ArrayList<>(), Expense.planned.PLANNED, true );
        Place start = new Accommodation("Harz Camping am Schierker Stern",
                new Coordinates("51.7569015", "10.6832768"),
                contact,
                arrive,departure, new ArrayList<>(Arrays.asList(expense1)), new ArrayList<>(),
                new ArrayList<>(), Accommodation.accommodationType.CAMPING);

        Place end = new Sight("Brocken", new Coordinates("51.806323199999994", "10.631225899999999"),
                new ContactDetails("039455 120", "verwaltung@brocken.harz", "Wernigerode",
                        "Harz", 1, "38879 ", "Deutschland"), arrive, departure,
                new ArrayList<>(Arrays.asList(expense1)), new ArrayList<>(), new ArrayList<>(), false);


        Connection connection = new Connection(title, arrive, departure, start, end, expense1);

        assertEquals(connection.getTitle(), title);
        assertEquals(connection.getArrive(), arrive);
        assertEquals(connection.getDeparture(), departure);
        assertEquals(connection.getStart(), start);
        assertEquals(connection.getEnd(), end);
        assertEquals(connection.getExpense(), expense1);

    }

    @Test
    public void should_create_connectionUnterfoehring() {
        String title = "Unterföhring";
        LocalDateTime arrive = LocalDateTime.of(2020,2,06,18,00) ;
        LocalDateTime departure = LocalDateTime.of(2020,2,10,14,30) ;
        ContactDetails contact = new ContactDetails(" +498192 933417", "contact@mti.tv", "Unterföhring",
                "Beta-Str.", 1, "85774", "Germany" );
        Expense expense1 = new Expense("Ausflug", "Wandertag", new Money(Currency.getInstance("EUR"),
                new BigDecimal(35)), new ArrayList<>(), Expense.planned.PLANNED, true );
        Place start = new Accommodation("MTI Teleport München GmbH",
                new Coordinates("48.1900696", "11.6569066"),
                contact,
                arrive,departure, new ArrayList<>(Arrays.asList(expense1)), new ArrayList<>(),
                new ArrayList<>(), Accommodation.accommodationType.AIRBNB);

        Place end = new Sight("Feringasee", new Coordinates("48.1981235", "11.6755371"),
                new ContactDetails("089 9503636", "Beach@Volleyball.sport", "Unterföhring",
                        "Am Feringasee", 22, "85774 ", "Deutschland"), arrive, departure,
                new ArrayList<>(Arrays.asList(expense1)), new ArrayList<>(), new ArrayList<>(), false);


        Connection connection = new Connection(title, arrive, departure, start, end, expense1);

        assertEquals(connection.getTitle(), title);
        assertEquals(connection.getArrive(), arrive);
        assertEquals(connection.getDeparture(), departure);
        assertEquals(connection.getStart(), start);
        assertEquals(connection.getEnd(), end);
        assertEquals(connection.getExpense(), expense1);

    }

    @Test
    public void should_create_connectionCantor() {
        String title = "CantorPfad";
        LocalDateTime arrive = LocalDateTime.of(1845,3,3,5,7) ;
        LocalDateTime departure = LocalDateTime.of(1918,1,6,9,11) ;
        ContactDetails contact = new ContactDetails("0345 6903156", "leitung(at)cantor.de", "Halle (Saale)",
                "Torstraße", 13, "06110", "Germany" );
        Expense expense1 = new Expense("TimeTravel", "History", new Money(Currency.getInstance("RUB"),
                new BigDecimal(35)), new ArrayList<>(), Expense.planned.CANCELED, false );
        Place start = new Accommodation("Geburtsort Sankt Petersburg",
                new Coordinates("59.9342802", "30.3350986"),
                contact,
                arrive,departure, new ArrayList<>(Arrays.asList(expense1)), new ArrayList<>(),
                new ArrayList<>(), Accommodation.accommodationType.COUCHSURF);

        Place end = new Sight("Lebensabend Halle", new Coordinates("51.4727992", "11.832289"),
                new ContactDetails("089 9503636", "Beach@Volleyball.sport", "Halle (Saale)",
                        "Delitzscher Str.", 70, "06112 ", "Deutschland"), arrive, departure,
                new ArrayList<>(Arrays.asList(expense1)), new ArrayList<>(), new ArrayList<>(), false);


        Connection connection = new Connection(title, arrive, departure, start, end, expense1);

        assertEquals(connection.getTitle(), title);
        assertEquals(connection.getArrive(), arrive);
        assertEquals(connection.getDeparture(), departure);
        assertEquals(connection.getStart(), start);
        assertEquals(connection.getEnd(), end);
        assertEquals(connection.getExpense(), expense1);

    }

    @Test
    public void should_create_connectionPisa() {
        String title = "Pisa";
        LocalDateTime arrive = LocalDateTime.of(2023,4,3,12,34) ;
        LocalDateTime departure = LocalDateTime.of(2023,4,5,23,59) ;
        ContactDetails contact = new ContactDetails("0112358/1321345589", "fibo@nacci.it", "Pisa",
                "Via Carlo Francesco Gabba", 17, "56122 ", "Italien" );
        Expense expense1 = new Expense("Sight", "Cultural", new Money(Currency.getInstance("EUR"),
                new BigDecimal(35)), new ArrayList<>(), Expense.planned.ISSUED, true );
        Place start = new Accommodation("Grand Hotel Bonanno",
                new Coordinates("43.7199624", "10.3840334"),
                contact,
                arrive,departure, new ArrayList<>(Arrays.asList(expense1)), new ArrayList<>(),
                new ArrayList<>(), Accommodation.accommodationType.HOTEL);

        Place end = new Sight("Torre di Pisa", new Coordinates("43.722952", "10.3944083"),
                new ContactDetails("089 9503636", "look@opapisa.it", "Pisa",
                        "Piazza del Duomo", 0, "56126  ", "Italien"), arrive, departure,
                new ArrayList<>(Arrays.asList(expense1)), new ArrayList<>(), new ArrayList<>(), false);


        Connection connection = new Connection(title, arrive, departure, start, end, expense1);

        assertEquals(connection.getTitle(), title);
        assertEquals(connection.getArrive(), arrive);
        assertEquals(connection.getDeparture(), departure);
        assertEquals(connection.getStart(), start);
        assertEquals(connection.getEnd(), end);
        assertEquals(connection.getExpense(), expense1);

    }
}
