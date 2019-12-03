package de.travelbuddy;

import java.time.LocalDateTime;

public class Accomodation extends Place {
    private accomodationType type;

    enum accomodationType
    {
        HOTEL,HOSTEL,CAMPING,COUCHSURF,AIRBNB
    }

    public Accomodation(String name, Coordinates coordiantes, Adress adress, LocalDateTime arrive,
                        LocalDateTime departure, Connection connectionToNextDestination, Expense expense,
                        accomodationType type) {
        super(name, coordiantes, adress, arrive, departure, connectionToNextDestination, expense);
        this.type = type;
    }

    public accomodationType getType() {return type;}

    public void setType(accomodationType type) {this.type = type;}
}
