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

    public static int getIDCounter() {
        return IDCounter;
    }

    public int getAccomodationID() {
        return accomodationID;
    }

    public void setAccomodationID(int accomodationID) {
        this.accomodationID = accomodationID;
    }

    public accomodationType getType() {
        return type;
    }

    public void setType(accomodationType type) {
        this.type = type;
    }
}
