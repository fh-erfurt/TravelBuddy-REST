package de.travelbuddy;

import java.time.LocalDateTime;

public class Sight extends Place {

    private static int IDCounter;
    private int sightID;
    private boolean indoor;

    public Sight(int placeID, String name, Coordinates coordinates, Adress adress, LocalDateTime arrive,
                 LocalDateTime departure, Connection connectionToNextDestination, Expense expense,
                 int sightID, boolean indoor) {

        super(placeID, name, coordinates, adress, arrive, departure, connectionToNextDestination, expense);
        IDCounter++;
        this.sightID = IDCounter;
        this.indoor = indoor;
    }

    public static int getDCounter() {return IDCounter;}

    public int getSightID() {return sightID;}

    public boolean isIndoor() {return indoor;}

    public void setIndoor(boolean indoor) {this.indoor = indoor;}
}
