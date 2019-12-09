package de.travelbuddy;

import static org.junit.jupiter.api.Assertions.*;

import de.travelbuddy.finance.Money;
import de.travelbuddy.place.Accommodation;
import de.travelbuddy.place.Place;
import de.travelbuddy.place.Sight;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;


public class ConnectionTest {
    @Test
    public void should_create_connection() {
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
                new ArrayList<>(), Accommodation.accommodationType.HOTEL);

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
}
