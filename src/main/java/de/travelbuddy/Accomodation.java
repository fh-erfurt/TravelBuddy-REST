package de.travelbuddy;

import java.time.LocalDateTime;

public class Accomodation extends Place {

    private static int IDCounter;
    private int accomodationID;
    private accomodationType type;

    enum accomodationType
    {
        HOTEL,HOSTEL,CAMPING,COUCHSURF,AIRBNB
    }

    public Accomodation(int placeID, String name, Coordinates coordiantes, Adress adress, LocalDateTime arrive, LocalDateTime departure, Connection connectionToNextDestination, Expense expense, int accomodationID, accomodationType type) {
        super(placeID, name, coordiantes, adress, arrive, departure, connectionToNextDestination, expense);
        IDCounter++;
        this.accomodationID = IDCounter;
        this.type = type;
    }
}
