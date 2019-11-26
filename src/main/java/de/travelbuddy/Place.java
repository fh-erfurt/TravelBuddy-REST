package de.travelbuddy;

import java.time.LocalDateTime;

public class Place {

    protected int PlaceID;
    protected String name;
    protected Coordinates coordiantes;
    protected Adress adress;
    protected LocalDateTime arrive;
    protected LocalDateTime departure;
    protected Connection connectionToNextDestination;
    protected Expense expense;

}
