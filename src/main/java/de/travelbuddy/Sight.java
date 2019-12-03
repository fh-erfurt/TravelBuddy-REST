package de.travelbuddy;

import java.time.LocalDateTime;

public class Sight extends Place {

    private boolean indoor;

    public Sight(String name, Coordinates coordinates, Adress adress, LocalDateTime arrive,
                 LocalDateTime departure, Connection connectionToNextDestination, Expense expense,
                 int sightID, boolean indoor) {

        super(name, coordinates, adress, arrive, departure, connectionToNextDestination, expense);
        this.indoor = indoor;
    }

    public boolean isIndoor() {return indoor;}

    public void setIndoor(boolean indoor) {this.indoor = indoor;}
}
