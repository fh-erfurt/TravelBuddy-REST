package de.travelbuddy.model.place;

import de.travelbuddy.model.finance.Expense;
import de.travelbuddy.model.place.exception.InvalidLatitudeException;
import de.travelbuddy.model.place.exception.InvalidLongitudeException;
import de.travelbuddy.utilities.InstanceHelper;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConnectionTest {
    @Test
    public void should_create_connectionBerlin() throws InvalidLatitudeException, InvalidLongitudeException {
        String title = "Berlin";
        LocalDateTime arrive = LocalDateTime.of(2020,7,25,16,45) ;
        LocalDateTime departure = LocalDateTime.of(2020,7,28,20,5) ;
        Expense expense1 = InstanceHelper.createExpense();
        Coordinates coordStart = new Coordinates();
        coordStart.setLatitude(52.516181);
        coordStart.setLongitude(13.376935);

        Coordinates coordEnd = new Coordinates();
        coordEnd.setLatitude(52.516275);
        coordEnd.setLongitude(13.377704);


        //Start
        Accommodation start = new Accommodation();
        start.setName("Generator Hostel");
        start.setCoordinates(coordStart);
        start.setContactDetails(InstanceHelper.createContactDetails());
        start.setArrive(arrive);
        start.setDeparture(departure);
        start.setExpenses(Map.of(expense1.getId(), expense1));
        start.setType(Accommodation.accommodationType.HOSTEL);

        //End
        Sight end = new Sight();
        end.setName("Brandenburger Tor");
        end.setCoordinates(coordEnd);
        end.setContactDetails(InstanceHelper.createContactDetails());
        end.setArrive(arrive);
        end.setDeparture(departure);
        end.setExpenses(Map.of(expense1.getId(), expense1));


        Connection connection = new Connection();
        connection.setTitle(title);
        connection.setArrive(arrive);
        connection.setDeparture(departure);
        connection.setStart(start);
        connection.setEnd(end);
        connection.setExpense(expense1);

        assertEquals(connection.getTitle(), title);
        assertEquals(connection.getArrive(), arrive);
        assertEquals(connection.getDeparture(), departure);
        assertEquals(connection.getStart(), start);
        assertEquals(connection.getEnd(), end);
        assertEquals(connection.getExpense(), expense1);

    }

    @Test
    public void duration_between_departure_and_arrive_inSeconds(){

        //Given
        Connection fromGToH = new Connection();
        fromGToH.setTitle("Pluto");
        fromGToH.setArrive(LocalDateTime.of(2045,9,5,5,55));
        fromGToH.setDeparture(LocalDateTime.of(2046,1,1,0,5));

        //When
        Duration GToH = fromGToH.getDuration();

        //Then
        assertEquals(10174200 , GToH.toSeconds());
    }

    @Test
    public void duration_between_departure_and_arrive_inRegularTimeUnit(){

        //Given
        Connection fromIToJ = new Connection();
        fromIToJ.setTitle("Pluto");
        fromIToJ.setArrive(LocalDateTime.of(2038,5,17,10,11));
        fromIToJ.setDeparture(LocalDateTime.of(2039,2,28,11,27));

        //When
        Duration GToH = fromIToJ.getDuration();

        //Then
        assertEquals(287 , GToH.toDays());
        assertEquals(6889 , GToH.toHours());
        assertEquals(413356 , GToH.toMinutes());
        assertEquals(24801360 , GToH.toSeconds());
    }

}
