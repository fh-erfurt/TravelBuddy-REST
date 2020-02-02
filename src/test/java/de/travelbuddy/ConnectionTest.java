package de.travelbuddy;

import de.travelbuddy.finance.Expense;
import de.travelbuddy.finance.Money;
import de.travelbuddy.place.*;
import de.travelbuddy.place.exception.InvalidLatitudeException;
import de.travelbuddy.place.exception.InvalidLongitudeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class ConnectionTest {
    @Test
    public void should_create_connectionBerlin() throws InvalidLatitudeException, InvalidLongitudeException {
        String title = "Berlin";
        LocalDateTime arrive = LocalDateTime.of(2020,7,25,16,45) ;
        LocalDateTime departure = LocalDateTime.of(2020,7,28,20,5) ;
        ContactDetails contact = new ContactDetails("9999999", "max@musterman.de", "Erfurt",
                "Altonaer Str.", 25, "99086", "Germany" );
        Expense expense1 = new Expense("Essen", "Pommes", new Money(Currency.getInstance("EUR"),
                new BigDecimal(35)), new ArrayList<>(), Expense.planned.ISSUED, false );
        Place start = new Accommodation("Generator Hostel",
                new Coordinates(52.516181, 13.376935),
                contact,
                arrive,departure, Map.of(expense1.getTitle(), expense1), new ArrayList<>(),
                new ArrayList<>(), Accommodation.accommodationType.HOSTEL);

        Place end = new Sight("Brandenburger Tor", new Coordinates(52.516275, 13.377704),
                new ContactDetails("9999999", "brandeburger@tor.de", "Berlin",
                        "Pariser Platz", 1, "10117", "Deutschland"), arrive, departure,
                Map.of(expense1.getTitle(), expense1), new ArrayList<>(), new ArrayList<>(), false);


        Connection connection = new Connection(title, arrive, departure, start, end, expense1);

        assertEquals(connection.getTitle(), title);
        assertEquals(connection.getArrive(), arrive);
        assertEquals(connection.getDeparture(), departure);
        assertEquals(connection.getStart(), start);
        assertEquals(connection.getEnd(), end);
        assertEquals(connection.getExpense(), expense1);

    }

    @Test
    public void duration_between_departure_and_arrive_inMinutes(){

        //Given
        Connection fromAToB = new Connection("Pisa",  LocalDateTime.of(2023,4,3,12,30),
                LocalDateTime.of(2023,4,3,13,15),
                null, null, null);

        //When
        Duration AToB = fromAToB.getDuration();

        //Then
        assertEquals(45, AToB.toMinutes());
    }

    @Test
    public void duration_between_departure_and_arrive_inHours(){

        //Given
        Connection fromCToD = new Connection("Mond",  LocalDateTime.of(2020,6,3,9,15),
                LocalDateTime.of(2020,6,5,20,15),
                null, null, null);

        //When
        Duration CToD = fromCToD.getDuration();

        //Then
        assertEquals(59, CToD.toHours());
    }

    @Test
    public void duration_between_departure_and_arrive_inDays(){

        //Given
        Connection fromEToF = new Connection("Mars",  LocalDateTime.of(2030,3,28,9,30),
                LocalDateTime.of(2030,7,28,21,30),
                null, null, null);

        //When
        Duration EToF = fromEToF.getDuration();

        //Then
        assertEquals(122, EToF.toDays());
    }

    @Test
    public void duration_between_departure_and_arrive_inSeconds(){

        //Given
        Connection fromGToH = new Connection("Pluto",  LocalDateTime.of(2045,9,5,5,55),
                LocalDateTime.of(2046,1,1,0,5),
                null, null, null);

        //When
        Duration GToH = fromGToH.getDuration();

        //Then
        assertEquals(10174200 , GToH.toSeconds());
    }

    @Test
    public void duration_between_departure_and_arrive_inRegularTimeUnit(){

        //Given
        Connection fromIToJ = new Connection("Saturn",  LocalDateTime.of(2038,5,17,10,11),
                LocalDateTime.of(2039,2,28,11,27),
                null, null, null);

        //When
        Duration GToH = fromIToJ.getDuration();

        //Then
        assertEquals(287 , GToH.toDays());
        assertEquals(6889 , GToH.toHours());
        assertEquals(413356 , GToH.toMinutes());
        assertEquals(24801360 , GToH.toSeconds());
    }

}
